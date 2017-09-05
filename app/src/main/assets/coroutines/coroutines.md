![](https://hbimg.b0.upaiyun.com/d797a1134ebba8ea30ac4d0fbed2d3c875d5528ee0d9a-T00GWc_fw658)

## Coroutines(协同程序)

> Coroutines are experimental in Kotlin 1.1. See details below

Some APIs initiate long-running operations (such as network IO, file IO, CPU- or GPU-intensive work, etc) and require the caller 
to block until they complete. Coroutines provide a way to avoid blocking a thread and replace it with a cheaper and more controllable 
operation: suspension of a coroutine.
(一些API启动长时间运行的操作（如网络IO，文件IO，CPU或GPU密集型工作等），并要求调用者阻止直到完成。 
 协调程序提供了一种避免阻塞线程并以更便宜和更可控的操作替代的方法：暂停协同程序。)
 
Coroutines simplify asynchronous programming by putting the complications into libraries. The logic of the program can be expressed 
sequentially in a coroutine, and the underlying library will figure out the asynchrony for us. The library can wrap relevant parts 
of the user code into callbacks, subscribe to relevant events, schedule execution on different threads (or even different machines!), 
and the code remains as simple as if it was sequentially executed.
(协调程序通过将并发放入库中来简化异步编程。 程序的逻辑可以在协同程序中顺序表达，底层的库将为我们找出不同步。 
 该库可以将用户代码的相关部分包含回调，订阅相关事件，在不同的线程（甚至是不同的机器上执行）进行调度，代码保持简单，就好像顺序执行。)
 
Many asynchronous mechanisms available in other languages can be implemented as libraries using Kotlin coroutines. 
This includes async/await from C# and ECMAScript, channels and select from Go, and generators/yield from C# and Python. 
See the description below for libraries providing such constructs.

### Blocking vs Suspending（封锁 VS 暂停）

Basically, coroutines are computations that can be suspended without blocking a thread. Blocking threads is often expensive, 
especially under high load, because only a relatively small number of threads is practical to keep around, so blocking one of 
them leads to some important work being delayed.
(基本上，协同程序是可以暂停而不阻塞线程的计算。 阻塞线程通常是昂贵的，特别是在高负载下，因为只有相对较少数量的线程才能实现，
 因此阻塞其中一个线程会导致一些重要的工作被延迟。)
 
Coroutine suspension is almost free, on the other hand. No context switch or any other involvement of the OS is required. 
And on top of that, suspension can be controlled by a user library to a large extent: as library authors, we can decide what 
happens upon a suspension and optimize/log/intercept according to our needs.
(另一方面，悬挂几乎是免费的。 无需上下文切换或操作系统的任何其他参与。 最重要的是，暂停可以由用户库控制：作为库的作者，
 我们可以根据我们的需要，决定暂停发生什么，优化/记录/拦截。)
 
Another difference is that coroutines can not be suspended at random instructions, but rather only at so called suspension points, 
which are calls to specially marked functions.

### Suspending functions（暂停函数）

A suspension happens when we call a function marked with the special modifier, suspend :

```
suspend fun doSomething(foo: Foo): Bar { 
    ...
}
```

Such functions are called suspending functions, because calls to them may suspend a coroutine (the library can decide to proceed 
without suspension, if the result for the call in question is already available). Suspending functions can take parameters and 
return values in the same manner as regular functions, but they can only be called from coroutines and other suspending functions. 
In fact, to start a coroutine, there must be at least one suspending function, and it is usually anonymous (i.e. it is a suspending 
lambda). Let's look at an example, a simplified async() function (from the kotlinx.coroutines library):
（这些功能称为挂起功能，因为对它们的调用可能会暂停协同程序（如果有关呼叫的结果已经可用，则该库可以决定继续进行而不中止）。 
  挂起的函数可以以与常规函数相同的方式获取参数和返回值，但只能从协程和其他挂起函数调用。 实际上，要启动协同程序，必须至少有一个暂停功能，
  通常是匿名的（即它是一个暂停的lambda）。 我们来看一个例子，一个简化的async（）函数（来自kotlinx.coroutines库）：）
  
```
fun <T> async(block: suspend () -> T)
```

Here, async() is a regular function (not a suspending function), but the block parameter has a function type with the suspend 
modifier: suspend () -> T . So, when we pass a lambda to async() , it is a suspending lambda, and we can call a
suspending function from it:

```
async { 
    doSomething(foo) 
    ...
}
```

To continue the analogy, await() can be a suspending function (hence also callable from within an async {} block) that suspends 
a coroutine until some computation is done and returns its result:
（为了继续类推，await（）可以是一个挂起函数（因此也可以在异步{}块内调用），暂停协同程序，直到完成一些计算并返回其结果：）

```
async { 
    ...
    val result = computation.await()
    ... 
}
```

More information on how actual async/await functions work in kotlinx.coroutines can be found here.

Note that suspending functions await() and doSomething() can not be called from a regular function like main() :

```
fun main(args: Array<String>) {
    doSomething() // ERROR: Suspending function called from a non-coroutine context
}
```

Also note that suspending functions can be virtual, and when overriding them, the suspend modifier has to be specified:

```
interface Base { 
    suspend fun foo()
}

class Derived: Base {
    override suspend fun foo() { 
        ... 
    }
}
```

**@RestrictsSuspension annotation**

Extension functions (and lambdas) can also be marked suspend , just like regular ones. This enables creation of DSLs and other APIs 
that users can extend. In some cases the library author needs to prevent the user from adding new ways of suspending a coroutine.
（扩展函数（和lambdas）也可以标记为暂停，就像常规功能一样。 这样可以创建用户可以扩展的DSL和其他API。 
  在某些情况下，库作者需要阻止用户添加新的挂起协议的方法。）
  
To achieve this, the @RestrictsSuspension annotation may be used. When a receiver class or interface R is annotated with it, 
all suspending extensions are required to delegate to either members of R or other extensions to it. Since extensions can't 
delegate to each other indefinitely (the program would not terminate), this guarantees that all suspensions happen through 
calling members of R that the author of the library can fully control.
（为了实现这一点，可以使用@RestrictsSuspension注释。 当接收器类或接口R被注释时，所有挂起的扩展都需要委派给R的成员或其他扩展。 
  由于扩展不能无限次地委托给对方（程序不会终止），因此这样可以保证所有的暂停都是通过调用库的作者完全控制的R的成员进行的。）
  
This is relevant in the rare cases when every suspension is handled in a special way in the library. For example, 
when implementing generators through the buildSequence() function described below, we need to make sure that any suspending 
call in the coroutine ends up calling either yield() or yieldAll() and not any other function. This is why SequenceBuilder 
is annotated with @RestrictsSuspension :
(这在少数情况下，在库中以特殊方式处理每一次暂停时都是相关的。 例如，当通过下面描述的buildSequence（）函数实现生成器时，
 我们需要确保协同程序中的任何挂起的调用最终调用yield（）或yieldAll（），而不是任何其他函数。 这就是为什么SequenceBuilder用@RestrictsSuspension注释：)

```
@RestrictsSuspension
public abstract class SequenceBuilder<in T> {
    ... 
}
```

See the sources on Github.

### The inner workings of coroutines（协同程序）

We are not trying here to give a complete explanation of how coroutines work under the hood, but a rough sense of what's going on 
is rather important.（我们不是想在这里做一个完整的解释，如何协调工作在引擎下，但粗略的意义是什么是相当重要的。）

Coroutines are completely implemented through a compilation technique (no support from the VM or OS side is required), 
and suspension works through code transformation. Basically, every suspending function (optimizations may apply, 
but we'll not go into this here) is transformed to a state machine where states correspond to suspending calls. 
Right before a suspension, the next state is stored in a field of a compiler-generated class along with relevant 
local variables, etc. Upon resumption of that coroutine, local variables are restored and the state machine proceeds 
from the state right after suspension.
(协调程序通过编译技术完全实现（不需要来自VM或OS端的支持），并通过代码转换进行暂停工作。 基本上，每个暂停功能（优化可能适用，但我们不会在这里进入）
 被转换到状态机，其中状态对应于挂起的呼叫。 在暂停之前，下一个状态存储在编译器生成的类的字段中以及相关的局部变量等。恢复该协程后，局部变量被恢复，
 状态机从暂停后的状态进行。)
 
A suspended coroutine can be stored and passed around as an object that keeps its suspended state and locals. The type of such 
objects is Continuation , and the overall code transformation described here corresponds to the classical Continuation- passing 
style. Consequently, suspending functions take an extra parameter of type Continuation under the hood.
(挂起的协同程序可以作为保持其暂停状态和当地人的对象进行存储和传递。 这样的对象的类型是Continuation，
这里描述的整体代码转换对应于经典的Continuation-passing风格。 因此，挂起的功能需要一个额外的继续类型参数。)

More details on how coroutines work may be found in this design document. Similar descriptions of async/await in other 
languages (such as C# or ECMAScript 2016) are relevant here, although the language features they implement may not be as general
as Kotlin coroutines.
(有关如何协调工作的更多详细信息可以在本设计文档中找到。 与其他语言（例如C＃或ECMAScript 2016）的异步/等待相似的描述在这里是相关的，
 尽管它们实现的语言特性可能不如Kotlin协同程序那么普遍。)
 
### Experimental status of coroutines

The design of coroutines is experimental, which means that it may be changed in the upcoming releases. When compiling 
coroutines in Kotlin 1.1, a warning is reported by default: The feature "coroutines" is experimental. To remove the warning, 
you need to specify an opt-in flag.

Due to its experimental status, the coroutine-related API in the Standard Library is put under the kotlin.coroutines.experimental 
package. When the design is finalized and the experimental status lifted, the final API will
be moved to kotlin.coroutines , and the experimental package will be kept around (probably in a separate artifact) for backward 
compatibility.

IMPORTANT NOTE: We advise library authors to follow the same convention: add the "experimental" (e.g. com.example.experimental ) suffix to your packages exposing coroutine-based APIs so that your library remains binary
compatible. When the final API is released, follow these steps:

* — copy all the APIs to com.example (without the experimental suffix),
* — keep the experimental package around for backward compatibility. 

This will minimize migration issues for your users.

### Standard APIs

Coroutines come in three main ingredients:

* — language support (i.s. suspending functions, as described above), 
* — low-level core API in the Kotlin Standard Library,
* — high-level APIs that can be used directly in the user code.

**Low-level API: kotlin.coroutines**

Low-level API is relatively small and should never be used other than for creating higher-level libraries. It consists of two main
packages:

— kotlin.coroutines.experimental with main types and primitives such as 
    — createCoroutine()
    — startCoroutine()
    — suspendCoroutine()
— kotlin.coroutines.experimental.intrinsics with even lower-level intrinsics such as suspendCoroutineOrReturn More details about the usage of these APIs can be found here.

**Generators API in kotlin.coroutines**

The only "application-level" functions in kotlin.coroutines.experimental are

— buildSequence() 

— buildIterator()

These are shipped within kotlin-stdlib because they are related to sequences. In fact, these functions (and we can limit 
ourselves to buildSequence() alone here) implement generators, i.e. provide a way to cheaply build a lazy sequence:

```
val fibonacciSeq = buildSequence { 
    var a = 0
    var b = 1
    
    yield(1)
    
    while (true) { 
        yield(a + b)
        val tmp = a + b a=b
        b = tmp
    } 
}
```

This generates a lazy, potentially infinite Fibonacci sequence by creating a coroutine that yields consecutive Fibonacci 
numbers by calling the yield() function. When iterating over such a sequence every step of the iterator executes another 
portion of the coroutine that generates the next number. So, we can take any finite list of numbers out of this sequence, e.g.
fibonacciSeq.take(8).toList() results in [1, 1, 2, 3, 5, 8, 13, 21] . And coroutines are cheap enough to make this practical.

To demonstrate the real laziness of such a sequence, let's print some debug output inside a call to buildSequence() :

```
val lazySeq = buildSequence { 
    print("START ")
    for (i in 1..5) { 
        yield(i)
        print("STEP ") 
    }
    print("END") 
}

// Print the first three elements of the sequence
lazySeq.take(3).forEach { print("$it ") }
```

Run the code above to see that if we print the first three elements, the numbers are interleaved with the STEP s the 
generating loop. This means that the computation is lazy indeed. To print 1 we only execute until the first yield(i) , 
and print START along the way. Then, to print 2 we need to proceed to the next yield(i) , and this prints STEP . Same for 3 . 
And the next
STEP never gets printed (as well as END ), because we never requested further elements of the sequence.

To yield a collection (or sequence) of values at once, the yieldAll() function is available:

```
val lazySeq = buildSequence { 
    yield(0)
    yieldAll(1..10) 
}
lazySeq.forEach { 
    print("$it ") 
}
```

The buildIterator() works similarly to buildSequence() , but returns a lazy iterator.

One can add custom yielding logic to buildSequence() by writing suspending extensions to the SequenceBuilder class (that
bears the @RestrictsSuspension annotation described above):

```
suspend fun SequenceBuilder<Int>.yieldIfOdd(x: Int) { 
    if (x % 2 != 0) yield(x)
}
val lazySeq = buildSequence {
    for (i in 1..10) yieldIfOdd(i)
}
```

**Other high-level APIs: kotlinx.coroutines**

Only core APIs related to coroutines are available from the Kotlin Standard Library. This mostly consists of core primitives and
interfaces that all coroutine-based libraries are likely to use.

Most application-level APIs based on coroutines are released as a separate library: kotlinx.coroutines. This library covers

— Platform-agnostic asynchronous programming with kotlinx-coroutines-core

    — this module includes Go-like channels that support select and other convenient primitives 
    — a comprehensive guide to this library is available here.
    
— APIs based on CompletableFuture from JDK 8: kotlinx-coroutines-jdk8
— Non-blocking IO (NIO) based on APIs from JDK 7 and higher: kotlinx-coroutines-nio
— Support for Swing ( kotlinx-coroutines-swing ) and JavaFx ( kotlinx-coroutines-javafx ) 
— Support for RxJava: kotlinx-coroutines-rx

These libraries serve as both convenient APIs that make common tasks easy and end-to-end examples of how to build coroutine-based
 libraries.

See [Kotlin Docs](https://kotlinlang.org/docs/reference/ "Kotlin Docs")