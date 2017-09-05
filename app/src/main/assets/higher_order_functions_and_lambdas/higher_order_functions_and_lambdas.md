![](https://hbimg.b0.upaiyun.com/5202dd93ed80fc03b82c731f2ef67a4f96f2cc9310169a-pN2DB0_fw658)

## Higher-Order Functions and Lambdas

### Higher-Order Functions

A higher-order function is a function that takes functions as parameters, or returns a function. 
(高阶函数是将函数作为参数或返回函数的函数)
A good example of such a function is lock() that takes a lock object and a function, acquires the lock, 
runs the function and releases the lock:

```
fun <T> lock(lock: Lock, body: () -> T): T { 

    lock.lock()
    
    try {
        return body()
    } finally {
        lock.unlock() 
    }
    
}
```

Let's examine the code above: body has a function type: () -> T , so it's supposed to be a function that takes no parameters 
and returns a value of type T . It is invoked inside the try-block, while protected by the lock , and its result is 
returned by the lock() function.

If we want to call lock() , we can pass another function to it as an argument (see function references):

```
fun toBeSynchronized() = sharedResource.operation() 
val result = lock(lock, ::toBeSynchronized)

```

Another, often more convenient way is to pass a lambda expression:
(这里类似es6 语法)

```
val result = lock(lock, { sharedResource.operation() })

```

Lambda expressions are described in more detail below, but for purposes of continuing this section, let's see a brief overview:

* — A lambda expression is always surrounded by curly braces,
* — Its parameters (if any) are declared before -> (parameter types may be omitted), 
* — The body goes after -> (when present).

In Kotlin, there is a convention that if the last parameter to a function is a function, and you're passing a lambda expression 
as the corresponding argument, you can specify it outside of parentheses（可以在括号之外指定他）:

```
lock (lock) { 
    sharedResource.operation()
}
```

Another example of a higher-order function would be map() :

```
fun <T, R> List<T>.map(transform: (T) -> R): List<R> { 

    val result = arrayListOf<R>()
    for (item in this)
        result.add(transform(item)) 
    return result
}
```

This function can be called as follows:

```
val doubled = ints.map { value -> value * 2 }
```

Note that the parentheses in a call can be omitted entirely if the lambda is the only argument to that call.
(请注意，如果lambda是该调用的唯一参数，调用中的括号可以完全省略。)

**it: implicit name of a single parameter**

One other helpful convention is that if a function literal has only one parameter, its declaration may be omitted (along with the ->),
and its name will be it :

```
ints.map { it * 2 }
```

These conventions allow to write LINQ-style code:

```
strings.filter { it.length == 5 }.sortBy { it }.map { it.toUpperCase() }
```

**Underscore for unused variables（未使用变量的下划线） (since 1.1)**

If the lambda parameter is unused, you can place an underscore instead of its name:

```
map.forEach { _, value -> println("$value!") }
```

**Destructuring（解构） in Lambdas (since 1.1)**

Destructuring in lambdas is described as a part of destructuring declarations.

### Inline Functions

Sometimes it is beneficial to enhance performance of higher-order functions using inline functions.
(有时使用内联函数来提高高阶函数的性能是有益的。)

### Lambda Expressions and Anonymous(匿名) Functions

A lambda expression or an anonymous function is a "function literal", i.e. a function that is not declared, 
but passed immediately as an expression. Consider the following example:

```
max(strings, { a, b -> a.length < b.length })
```

Function max is a higher-order function, i.e. it takes a function value as the second argument. This second argument is an 
expression that is itself a function, i.e. a function literal. As a function, it is equivalent to

```
fun compare(a: String, b: String): Boolean = a.length < b.length
```

**Function Types**

For a function to accept another function as a parameter, we have to specify a function type for that parameter. 
For example the above mentioned function max is defined as follows:

```
fun <T> max(collection: Collection<T>, less: (T, T) -> Boolean): T? { 
    var max: T? = null
    for (it in collection)
        if (max == null || less(max, it)) 
            max = it
    return max 
}
```

The parameter less is of type (T, T) -> Boolean , i.e. a function that takes two parameters of type T and returns a Boolean : true 
if the first one is smaller than the second one.

In the body, line 4, less is used as a function: it is called by passing two arguments of type T .

A function type is written as above, or may have named parameters, if you want to document the meaning of each parameter.

```
val compare: (x: T, y: T) -> Int = ...
```

**Lambda Expression Syntax**

The full syntactic form of lambda expressions, i.e. literals of function types, is as follows:

```
val sum = { x: Int, y: Int -> x + y }
```

A lambda expression is always surrounded by curly braces, parameter declarations in the full syntactic form go inside 
parentheses and have optional type annotations, the body goes after an -> sign. If the inferred return type of the lambda is not
Unit , the last (or possibly single) expression inside the lambda body is treated as the return value.

If we leave all the optional annotations out, what's left looks like this:

```
val sum: (Int, Int) -> Int = { x, y -> x + y }
```

It's very common that a lambda expression has only one parameter. If Kotlin can figure the signature out itself, it allows us not to
declare the only parameter, and will implicitly declare it for us under the name it :

```
ints.filter { it > 0 } // this literal is of type '(it: Int) -> Boolean'
```

We can explicitly return a value from the lambda using the qualified return syntax. Otherwise, the value of the last expression 
is implictly returned. Therefore, the two following snippets are equivalent:

```
ints.filter {
    val shouldFilter = it > 0 shouldFilter
}

ints.filter {
    val shouldFilter = it > 0 return@filter shouldFilter
}
```

Note that if a function takes another function as the last parameter, the lambda expression argument can be passed outside the 
parenthesized argument list. See the grammar for callSuffix.

**Anonymous Functions**

One thing missing from the lambda expression syntax presented above is the ability to specify the return type of the function. 
In most cases, this is unnecessary because the return type can be inferred automatically. However, if you do need to specify it 
explicitly, you can use an alternative syntax: an anonymous function.

```
fun(x: Int, y: Int): Int = x + y
```

An anonymous function looks very much like a regular function declaration, except that its name is omitted. Its body can be
either an expression (as shown above) or a block:

```
fun(x: Int, y: Int): Int { 
    return x + y
}
```

The parameters and the return type are specified in the same way as for regular functions, except that the parameter types can be 
omitted if they can be inferred from context:

```
ints.filter(fun(item) = item > 0)
```

The return type inference for anonymous functions works just like for normal functions: the return type is inferred automatically 
for anonymous functions with an expression body and has to be specified explicitly (or is assumed to be Unit ) for anonymous 
functions with a block body.

Note that anonymous function parameters are always passed inside the parentheses. The shorthand syntax allowing to leave the 
function outside the parentheses works only for lambda expressions.

One other difference between lambda expressions and anonymous functions is the behavior of non-local returns. A return statement 
without a label always returns from the function declared with the fun keyword. This means that a return inside a lambda expression 
will return from the enclosing function, whereas a return inside an anonymous function will return from the anonymous function itself.

**Closures(闭合)**

A lambda expression or anonymous function (as well as a local function and an object expression) can access its closure, i.e. 
the variables declared in the outer scope. Unlike Java, the variables captured in the closure can be modified:
(lambda表达式或匿名函数（以及本地函数和对象表达式）可以访问其闭包，即外部范围中声明的变量。 与Java不同，可以修改闭包中捕获的变量：)

```
var sum = 0

ints.filter { it > 0 }.forEach {
    sum += it 
}

print(sum)
```

**Function Literals with Receiver**

Kotlin provides the ability to call a function literal with a specified receiver object. Inside the body of the function literal, 
you can call methods on that receiver object without any additional qualifiers. This is similar to extension functions, which allow 
you to access members of the receiver object inside the body of the function. One of the most important examples of their usage is 
Type-safe Groovy-style builders.

The type of such a function literal is a function type with receiver:

```
sum : Int.(other: Int) -> Int
```

The function literal can be called as if it were a method on the receiver object:

```
1.sum(2)
```

The anonymous function syntax allows you to specify the receiver type of a function literal directly. This can be useful if you need
to declare a variable of a function type with receiver, and to use it later.

```
val sum = fun Int.(other: Int): Int = this + other
```

Lambda expressions can be used as function literals with receiver when the receiver type can be inferred from context.

```
class HTML {
    fun body() { ... }
}

fun html(init: HTML.() -> Unit): HTML {

 val html = HTML() // create the receiver object 
 html.init() // pass the receiver object to the lambda return html
 
}

html { // lambda with receiver begins here 

    body() // calling a method on the receiver object
    
}
```

See [Kotlin Docs](https://kotlinlang.org/docs/reference/ "Kotlin Docs")









 