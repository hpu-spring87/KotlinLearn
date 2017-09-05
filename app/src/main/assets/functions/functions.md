![](https://hbimg.b0.upaiyun.com/cebbb6cc4197b5eeb4422b89d89078956c8579241c43b5-PGo2RY_fw658)

## Functions

### Function Declarations

Functions in Kotlin are declared using the fun keyword

```
fun double(x: Int): Int { 
    return 2*x
}
```

### Function Usage

Calling functions uses the traditional approach

```
val result = double(2)
```

Calling member functions uses the dot notation(点符号)

```
Sample().foo() // create instance of class Sample and call foo
```

**Infix notation(中间符号)**

Functions can also be called using infix notations when

* — They are member functions or extension functions 
* — They have a single parameter
* — They are marked with the infix keyword

```
// Define extension to Int(将扩展名定义为Int)

infix fun Int.shl(x: Int): Int {
    ...
}

// call extension function using infix notation（使用中缀符号调用函数）

1 shl 2

// is the same as 

1.shl(2)
```

**Parameters**

Function parameters are defined using Pascal notation, i.e. name: type. Parameters are separated using commas. Each parameter must 
be explicitly typed.

```
fun powerOf(number: Int, exponent: Int) {
    ...
}
```

**Default Arguments**

Function parameters can have default values, which are used when a corresponding argument is omitted. This allows for a reduced 
number of overloads compared to other languages.

```
fun read(b: Array<Byte>, off: Int = 0, len: Int = b.size()) { 
    ...
}
```

Default values are defined using the = after type along with the value.

Overriding methods always use the same default parameter values as the base method. When overriding a method with default
parameters values, the default parameter values must be omitted from the signature:

```
open class A {
    open fun foo(i: Int = 10) { 
        ...
     }
}

class B : A() {
    override fun foo(i: Int) { 
        ... 
    } // no default value allowed
}
```

**Named Arguments**

Function parameters can be named when calling functions. This is very convenient when a function has a high number of parameters 
or default ones.

Given the following function

```
fun reformat(str: String,
             normalizeCase: Boolean = true,
             upperCaseFirstLetter: Boolean = true,
             divideByCamelHumps: Boolean = false,
             wordSeparator: Char = ' '){
             
             //...
             
}

```

we could call this using default arguments

```
reformat(str)
```

However, when calling it with non-default, the call would look something like

```
reformat(str, true, true, false, '_')
```

With named arguments we can make the code much more readable

```
reformat(str,
        normalizeCase = true, 
        upperCaseFirstLetter = true, 
        divideByCamelHumps = false, 
        wordSeparator = '_'
)
```

and if we do not need all arguments

```
reformat(str, wordSeparator = '_')
```

Note that the named argument syntax cannot be used when calling Java functions, because Java bytecode does not always preserve 
names of function parameters.
(请注意，调用Java函数时不能使用命名参数语法，因为Java字节码并不总是保留函数参数的名称。)

**Unit-returning functions**

If a function does not return any useful value, its return type is Unit . Unit is a type with only one value - Unit . 
This value does not have to be returned explicitly(显示返回)

```
fun printHello(name: String?): Unit { 

    if (name != null)
        println("Hello ${name}") 
    else
        println("Hi there!")
    // `return Unit` or `return` is optional
    
}
```

The Unit return type declaration is also optional. The above code is equivalent to

```
fun printHello(name: String?) { 
    ...
}
```

**Single-Expression functions(单表达式函数)**

When a function returns a single expression, the curly braces can be omitted and the body is specified after a = symbol
(可以省略花括号，并在a=之后指定正文)

```
fun double(x: Int): Int = x * 2
```

Explicitly declaring the return type is optional when this can be inferred by the compiler
（显示的返回是可选的，前提是编译器可以推断返回类型）

```
fun double(x: Int) = x * 2
```

**Explicit return types（显示返回类型）**

Functions with block body must always specify return types explicitly, unless it's intended for them to return Unit , in which case 
it is optional. Kotlin does not infer return types for functions with block bodies because such functions may have complex control 
flow in the body, and the return type will be non-obvious to the reader (and sometimes even for the compiler).

**Variable number of arguments （可变数量的参数）(Varargs)（）**

A parameter of a function (normally the last one) may be marked with vararg modifier:

```
fun <T> asList(vararg ts: T): List<T> { 

    val result = ArrayList<T>()
    
    for (t in ts) // ts is an Array
        result.add(t) 
    
    return result
}
```

allowing a variable number of arguments to be passed to the function:

```
val list = asList(1, 2, 3)
```

Inside a function a vararg -parameter of type T is visible as an array of T , i.e. the ts variable in the example above has type
Array<out T> .

Only one parameter may be marked as vararg . If a vararg parameter is not the last one in the list, values for the following 
parameters can be passed using the named argument syntax, or, if the parameter has a function type, by passing a lambda outside 
parentheses.

When we call a vararg -function, we can pass arguments one-by-one, e.g. asList(1, 2, 3) , or, if we already have an array and 
want to pass its contents to the function, we use the spread operator (prefix the array with * ):
(当我们调用vararg函数时，我们可以逐个传递参数，例如 asList（1，2，3），或者，如果我们已经有一个数组并且要将其内容传递给函数，
 我们使用扩展运算符（数组前缀*）：)
 
```
val a = arrayOf(1, 2, 3)
val list = asList(-1, 0, *a, 4)
```

### Function Scope(函数范围)

> In Kotlin functions can be declared at top level in a file, meaning you do not need to create a class to hold a function, 
  like languages such as Java, C# or Scala. In addition to top level functions, Kotlin functions can also be declared local, 
  as member functions and extension functions.
  (在Kotlin中，函数可以在一个文件的顶层声明，这意味着您不需要创建一个类来保存一个函数，比如Java，C＃或Scala等语言。 除了顶级功能之外，
   Kotlin功能也可以被声明为本地，作为成员函数和扩展功能。)
   
**Local Functions**

Kotlin supports local functions, i.e. a function inside another function

```
fun dfs(graph: Graph) {
    fun dfs(current: Vertex, visited: Set<Vertex>) {
    
        if (!visited.add(current)) return 
        for (v in current.neighbors)
            dfs(v, visited)
    }
    
    dfs(graph.vertices[0], HashSet()) 
}
```

Local function can access local variables of outer functions (i.e. the closure), so in the case above, the visited can be a 
local variable

```
fun dfs(graph: Graph) {

    val visited = HashSet<Vertex>() 
    
    fun dfs(current: Vertex) {
        if (!visited.add(current)) return 
        
        for (v in current.neighbors)
            dfs(v) 
    }
    
    dfs(graph.vertices[0]) 
}
```

**Member Functions**

A member function is a function that is defined inside a class or object

```
class Sample() {
    fun foo() { 
        print("Foo") 
    }
}
```

Member functions are called with dot notation

```
Sample().foo() // creates instance of class Sample and calls foo
```

For more information on classes and overriding members see Classes and Inheritance

**Generic Functions**

Functions can have generic parameters which are specified using angle brackets before the function name
(函数可以有通用参数，它们在函数名之前用尖括号指定)

```
fun <T> singletonList(item: T): List<T> { 
    // ...
}
```

For more information on generic functions see Generics

### Inline Functions(内联函数)

### Extension Functions(扩展函数)

Extension functions are explained in their own section

### Higher-Order Functions and Lambdas(高阶函数和Lambdas表达式)

Higher-Order functions and Lambdas are explained in their own section

### Tail recursive functions（尾递归函数）

Kotlin supports a style of functional programming known as tail recursion. This allows some algorithms that would normally 
be written using loops to instead be written using a recursive function, but without the risk of stack overflow. When a function 
is marked with the tailrec modifier and meets(满足) the required form（所需形式）, the compiler optimises out the recursion（编译器会优化递归）
, leaving behind a fast and efficient loop based version instead（从而避开快速有效的循环版本。）.

```
 tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))
```

This code calculates the fixpoint（固定点） of cosine, which is a mathematical constant（数学常量）. It simply calls Math.cos repeatedly starting 
at 1.0 until the result doesn't change any more, yielding（得到） a result of 0.7390851332151607. The resulting code is equivalent to 
this more traditional style:

```
private fun findFixPoint(): Double { 

    var x = 1.0
    
    while (true) {
    
        val y = Math.cos(x) 
        if (x == y) return y 
        x=y
    
    } 
}
```

To be eligible for the tailrec modifier, a function must call itself as the last operation it performs. You cannot use tail 
recursion when there is more code after the recursive call, and you cannot use it within try/catch/finally blocks. 
Currently tail recursion is only supported in the JVM backend.
（要符合tailrec修饰符的条件，函数必须将其自身称为最后一个操作。 当递归调用之后有更多的代码时，不能使用尾递归，并且不能在try / catch / finally块
  中使用它。 目前的尾递归仅在JVM后端才支持。）
  
See [Kotlin Docs](https://kotlinlang.org/docs/reference/ "Kotlin Docs")
  










  











