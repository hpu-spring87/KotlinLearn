![](https://hbimg.b0.upaiyun.com/5e593f9dd894bf177ff946c76e184b4d0c11c3f212aff3-HjBTkI_fw658)

## Basic Syntax

### Defining packages

Package specification should be at the top of the source file:

```
package my.demo 

import java.util.* 

// ...
```

It is not required to match directories and packages: source files can be placed arbitrarily in the file system.
(不需要匹配目录和包：源文件可以任意放在文件系统中。)

### Defining functions

Function having two Int parameters with Int return type:

```
fun sum(a: Int, b: Int): Int { 
    return a + b
}
```

Function with an expression body and inferred return type:

```
fun sum(a: Int, b: Int) = a + b
```

Function returning no meaningful value:

```
fun printSum(a: Int, b: Int): Unit { 
    println("sum of $a and $b is ${a + b}")
}
```

Unit return type can be omitted(省略):

```
fun printSum(a: Int, b: Int) {
    println("sum of $a and $b is ${a + b}")
}
```

### Defining local variables

Assign-once (read-only) local variable:

```
val a: Int = 1 // immediate assignment
val b = 2 // `Int` type is inferred
val c: Int // Type required when no initializer is provided 
c = 3 // deferred assignment
```

Mutable(可变的) variable:

```
var x = 5 // `Int` type is inferred 
x += 1
```

### Comments(注释)

Just like Java and JavaScript, Kotlin supports end-of-line and block comments.

```
// This is an end-of-line comment
/* This is a block comment 
   on multiple lines. */
```

Unlike Java, block comments in Kotlin can be nested(嵌套).

### Using string templates

```
var a = 1
// simple name in template: val s1 = "a is $a"
a=2
// arbitrary expression in template:
val s2 = "${s1.replace("is", "was")}, but now is $a"
```

### Using conditional expressions(条件表达式)

```
fun maxOf(a: Int, b: Int): Int { 
    if (a > b) {
        return a 
    } else {
        return b 
    }
}
```

Using if as an expression:

```
fun maxOf(a: Int, b: Int) = if (a > b) a else b
```

### Using nullable values and checking for null

A reference must be explicitly(明确地) marked as nullable when null value is possible.

Return null if str does not hold an integer:

```
fun parseInt(str: String): Int? { 
    // ...
}
```

Use a function returning nullable value:

```
fun printProduct(arg1: String, arg2: String) { 
    val x = parseInt(arg1)
    val y = parseInt(arg2)
    // Using `x * y` yields error because they may hold nulls.
    if (x != null && y != null) {
        // x and y are automatically cast to non-nullable after null check 
        println(x * y)
    }
    else {
        println("either '$arg1' or '$arg2' is not a number") }
    }
```

or

```
// ...
if (x == null) {
    println("Wrong number format in arg1: '${arg1}'") 
    return
}
if (y == null) {
    println("Wrong number format in arg2: '${arg2}'")
    return
}
// x and y are automatically cast to non-nullable after null check
println(x * y)
```

Using type checks and automatic casts

The is operator checks if an expression is an instance of a type. 
If an immutable local variable or property is checked for a specific type, there's no need to cast it explicitly:

```
fun getStringLength(obj: Any): Int? { 
    if (obj is String) {
        // `obj` is automatically cast to `String` in this branch
        return obj.length 
    }
    // `obj` is still of type `Any` outside of the type-checked branch
    return null
}
```

or

```
fun getStringLength(obj: Any): Int? { 
    if (obj !is String) return null
    
    // `obj` is automatically cast to `String` in this branch
    return obj.length 
}
```

or even

```
fun getStringLength(obj: Any): Int? {
    // `obj` is automatically cast to `String` on the right-hand side of `&&` 
    if (obj is String && obj.length > 0) {
        return obj.length 
    }
    return null
}
```

### Using a for loop

```
val items = listOf("apple", "banana", "kiwi") 
for (item in items) {
    println(item) 
}
```

or

```
val items = listOf("apple", "banana", "kiwi") 
//indices (索引)
for (index in items.indices) {
    println("item at $index is ${items[index]}") 
}
```

### Using a while loop

```
val items = listOf("apple", "banana", "kiwi") 
var index = 0
while (index < items.size) {
    println("item at $index is ${items[index]}")
    index++ 
}
```

### Using when expression（表达式）

```
fun describe(obj: Any): String = 
    when (obj) {
        1  -> "One"
        "Hello" -> "Greeting"
        is Long -> "Long"
        !is String -> "Not a string"
        else  -> "Unknown"
   }   
```

### Using ranges(值域)

Check if a number is within a range using in operator:

```
val x = 10
val y = 9
if (x in 1..y+1) {
    println("fits in range") 
}  
```

Check if a number is out of range:

```
val list = listOf("a", "b", "c")
if (-1 !in 0..list.lastIndex) { 
    println("-1 is out of range")
}
if (list.size !in list.indices) {
    println("list size is out of valid list indices range too") 
}
```

Iterating over a range:(范围内迭代)

```
for (x in 1..5) { 
    print(x)
}
```

or over a progression:(一个迭代周期内累加/累进)

step ： 每个迭代周期内的累进的单位量

```
for (x in 1..10 step 2) { 
    print(x)
}
//运行结果：13579
for (x in 9 downTo 0 step 3) {
    print(x) 
}
//运行结果：9630
```

### Using collections

Iterating over a collection:（迭代集合）

```
for (item in items) { 
    println(item)
}
```

Checking if a collection contains an object using in operator:

```
when {
    "orange" in items -> println("juicy")
    "apple" in items -> println("apple is fine too")
}
```

Using lambda expressions to filter and map collections:

```
fruits
    .filter { it.startsWith("a") } 
    .sortedBy { it }
    .map { it.toUpperCase() } 
    .forEach { println(it) }
```

See [Kotlin Docs](https://kotlinlang.org/docs/reference/ "Kotlin Docs")