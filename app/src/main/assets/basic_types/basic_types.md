![](https://hbimg.b0.upaiyun.com/10b58e1feec843cdcfa7c79850ced6c499478e821879fe-56XOv9_fw658)
## Basics

### Basic Types

In Kotlin, everything is an object in the sense that we can call member functions and properties on any variable. 
Some types are built-in, because their implementation is optimized, but to the user they look like ordinary classes. 
In this section we describe most of these types: numbers, characters, booleans and arrays.

(在Kotlin中，一切都是一个对象，我们可以在任何变量上调用成员函数和属性。 一些类型是内置的，因为它们的实现被优化，但是对于他们来说，
它们看起来像普通类。 在本节中，我们将描述大多数类型：数字，字符，布尔和数组。)

### Numbers

Kotlin handles numbers in a way close to Java, but not exactly the same. For example, there are no implicit widening 
conversions for numbers,(隐士向上转换) and literals（字符串） are slightly different in some cases.

Kotlin provides the following built-in（內建） types representing numbers (this is close to Java):

Type | Bit width
---- | ---- 
Double | 64 
Float | 32 
Long | 64 
Int |  32 
Short | 16 
Byte | 8

Note that characters are not numbers in Kotlin.

**Literal Constants(字面常量)**

There are the following kinds of literal constants for integral values:

* — Decimals: 123

* — Longs are tagged by a capital L : 123L

* — Hexadecimals: 0x0F

* — Binaries: 0b00001011

NOTE: Octal(八进制) literals are not supported.

Kotlin also supports a conventional notation for floating-point numbers:
（Kotlin还支持一个常规的浮点数符号：）

* — Doubles by default: 123.5 , 123.5e10 

* — Floats are tagged by f or F : 123.5f

**Underscores in numeric literals（数字文字的下划线） (since 1.1)**

You can use underscores to make number constants more readable:

```
val oneMillion = 1_000_000
val creditCardNumber = 1234_5678_9012_3456L
val socialSecurityNumber = 999_99_9999L
val hexBytes = 0xFF_EC_DE_5E
val bytes = 0b11010010_01101001_10010100_10010010
```

**Representation**

On the Java platform, numbers are physically stored as JVM primitive types, 
unless we need a nullable number reference (e.g. Int? ) or generics are involved. 
In the latter cases numbers are boxed.

Note that boxing of numbers does not necessarily preserve identity:

```
val a: Int = 10000
print(a === a) // Prints 'true'
val boxedA: Int? = a
val anotherBoxedA: Int? = a
print(boxedA === anotherBoxedA) // !!!Prints 'false'!!!
```

On the other hand, it preserves equality:

```
val a: Int = 10000
print(a == a) // Prints 'true'
val boxedA: Int? = a
val anotherBoxedA: Int? = a
print(boxedA == anotherBoxedA) // Prints 'true'
```

**Explicit Conversions（显式转换）**

Due to different representations, smaller types are not subtypes of bigger ones.
 If they were, we would have troubles of the following sort:
 
```
// Hypothetical code, does not actually compile:
val a: Int? = 1 // A boxed Int (java.lang.Integer)
val b: Long? = a // implicit conversion yields a boxed Long (java.lang.Long)
print(a == b) // Surprise! This prints "false" as Long's equals() check for other part to be Long as well
```

So not only identity, but even equality would have been lost silently all over the place.

As a consequence, smaller types are NOT implicitly converted to bigger types. This means that we cannot assign a value of type
Byte to an Int variable without an explicit conversion
（因此，较小的类型不会被隐式转换为更大的类型。 这意味着我们无法分配类型的值
 字节到Int变量而不进行显式转换）
 
```
 val b: Byte = 1 // OK,literals are checked statically 
 val i: Int = b // ERROR
```

We can use explicit conversions to widen numbers
（我们可以使用显式转换来扩大数字）

```
val i: Int = b.toInt() // OK: explicitly widened
```

Every number type supports the following conversions:

* — toByte(): Byte
* — toShort(): Short 
* — toInt(): Int
* — toLong(): Long
* — toFloat(): Float 
* — toDouble(): Double 
* — toChar(): Char

Absence of implicit conversions is rarely noticeable because the type is inferred from the context, 
and arithmetical operations are overloaded for appropriate conversions, for example
（缺少隐式转换是很少引人注目的，因为类型是从上下文推断出来的，算术运算被重载以进行适当的转换，例如）

```
val l = 1L + 3 // Long + Int => Long
```

**Operations**

Kotlin supports the standard set of arithmetical operations over numbers, which are declared as members of 
appropriate classes (but the compiler optimizes the calls down to the corresponding instructions).

As of bitwise operations, there're no special characters for them, but just named functions that can be 
called in infix form, for example:
（对于按位操作，它们没有特殊的字符，但是只能使用可以以中缀形式调用的命名函数，例如：）

```
val x = (1 shl 2) and 0x000FF000
```

Here is the complete list of bitwise operations (available for Int and Long only):

* — shl(bits) – signed shift left (Java's << )
* — shr(bits) – signed shift right (Java's >> )
* — ushr(bits) – unsigned shift right (Java's >>> ) — and(bits) – bitwise and
* — or(bits) – bitwise or
* — xor(bits) – bitwise xor
* — inv() – bitwise inversion

### Characters

Characters are represented by the type Char . They can not be treated directly as numbers

```
fun check(c: Char) {
    if (c == 1) { // ERROR: incompatible types（不兼容类型）
        // ...
    } 
}
```

Character literals go in single quotes: '1' . Special characters can be escaped using a backslash. 
The following escape sequences are supported: \t , \b , \n , \r , \' , \" , \\ and \$ . 
To encode any other character, use the Unicode escape sequence syntax: '\uFF00' .

(字符文字用单引号表示：'1'。 可以使用反斜杠转义特殊字符。 支持以下转义序列：\ t，\ b，\ n，\ r，\'，\“，\\和\ 
$要对任何其他字符进行编码，请使用Unicode转义序列语法：'\ uFF00'。)

We can explicitly convert a character to an Int number:

```
fun decimalDigitValue(c: Char): Int { 
    if (c !in '0'..'9')
        throw IllegalArgumentException("Out of range")
    return c.toInt() - '0'.toInt() // Explicit conversions to numbers
}
```

Like numbers, characters are boxed when a nullable reference is needed. Identity is not preserved by the boxing operation.

### Booleans

The type Boolean represents booleans, and has two values: true and false. 
Booleans are boxed if a nullable reference is needed.
Built-in operations on booleans include

* — || –lazydisjunction 
* — && –lazyconjunction 
* — ! - negation

### Arrays

Arrays in Kotlin are represented by the Array class, that has get and set functions (that turn into [] by operator overloading conventions), 
and size property, along with a few other useful member functions:

```
class Array<T> private constructor() { 
    val size: Int
    operator fun get(index: Int): T
    operator fun set(index: Int, value: T): Unit
    operator fun iterator(): Iterator<T>
    // ...
}
```

To create an array, we can use a library function arrayOf() and pass the item values to it, so that arrayOf(1, 2, 3) creates an array [1, 2, 3]. 
Alternatively, the arrayOfNulls() library function can be used to create an array of a given size filled with null elements.

Another option is to use a factory function that takes the array size and the function that can return the initial value of each array element given its index:

```
// Creates an Array<String> with values ["0", "1", "4", "9", "16"]
val asc = Array(5, { i -> (i * i).toString() })
```

As we said above, the [] operation stands for calls to member functions get() and set() .

Note: unlike Java, arrays in Kotlin are invariant(不变的). This means that Kotlin does not let us assign an Array<String> to an
Array<Any> , which prevents a possible runtime failure (but you can use Array<out Any> 

Kotlin also has specialized classes to represent arrays of primitive types without boxing overhead: 
ByteArray , ShortArray , IntArray and so on. These classes have no inheritance relation to the Array class, 
but they have the same set of methods and properties. Each of them also has a corresponding factory function:

```
 val x: IntArray = intArrayOf(1, 2, 3) 
 x[0] = x[1] + x[2]
```

### Strings

Strings are represented by the type String . Strings are immutable. Elements of a string are characters that can be accessed by the indexing operation: s[i] . 
A string can be iterated over with a for-loop:

```
for (c in str) { 
    println(c)
}
```

**String Literals**

Kotlin has two types of string literals: escaped strings that may have escaped characters in them and raw strings 
that can contain newlines and arbitrary text. An escaped string is very much like a Java string:

```
val s = "Hello, world!\n"
```

Escaping is done in the conventional way, with a backslash. See Characters above for the list of supported escape sequences.
A raw string is delimited by a triple quote ( """ ), contains no escaping and can contain newlines and any other characters:
（以传统的方式完成转义，具有反斜杠。 有关支持的转义序列的列表，请参阅上述字符。
 原始字符串由三重引号（“”“）分隔，不包含转义，并且可以包含换行符和任何其他字符：）
 
```
val text = """
    for (c in "foo")
        print(c) 
"""
```

You can remove leading whitespace with trimMargin() function:（删除前面空格）

```
val text = """
    |Tell me and I forget. 
    |Teach me and I remember. 
    |Involve me and I learn. 
    |(Benjamin Franklin) 
    """.trimMargin()
```
By default | is used as margin prefix, but you can choose another character and pass it as a parameter, like trimMargin(">") .

**String Templates**

Strings may contain template expressions, i.e. pieces of code that are evaluated and whose results are concatenated into the string. 
A template expression starts with a dollar sign ($) and consists of either a simple name:

```
val i = 10
val s = "i = $i" // evaluates to "i = 10"
```

or an arbitrary expression in curly braces:

```
val s = "abc"
val str = "$s.length is ${s.length}" // evaluates to "abc.length is 3"
```

Templates are supported both inside raw strings and inside escaped strings. If you need to represent a literal $ character in a raw string (which doesn't support backslash escaping), 
you can use the following syntax:
（原始字符串和转义字符串内都支持模板。 如果您需要在原始字符串（不支持反斜杠转义）中表示文字$字符，则可以使用以下语法：）

```
val price = """ 
    ${'$'}9.99
"""
```

See [Kotlin Docs](https://kotlinlang.org/docs/reference/ "Kotlin Docs")

