## Extensions(扩展)

Kotlin, similar to C# and Go su, provides the ability to extend a class with new functionality without having to inherit 
from the class or use any type of design pattern such as Decorator. This is done via special declarations called extensions. 
Kotlin supports extension functions and extension properties.

### Extension Functions

To declare an extension function, we need to prefix its name with a receiver type, i.e. the type being extended. 
The following adds a swap function to MutableList<Int> :

```
fun MutableList<Int>.swap(index1: Int, index2: Int) {

    val tmp = this[index1] // 'this' corresponds to the list 
    
    this[index1] = this[index2]
    
    this[index2] = tmp
}
```

The this keyword inside an extension function corresponds to the receiver object (the one that is passed before the dot). 
Now, we can call such a function on any MutableList<Int> :

```
val l = mutableListOf(1, 2, 3)

l.swap(0, 2) // 'this' inside 'swap()' will hold the value of 'l'
```
  
Of course, this function makes sense for any MutableList<T> , and we can make it generic:

```
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    
    val tmp = this[index1] // 'this' corresponds to the list 
    
    this[index1] = this[index2]
    
    this[index2] = tmp
}
```
  
We declare the generic type parameter before the function name for it to be available in the receiver type expression. 
See Generic functions.

### Extensions are resolved statically

Extensions do not actually modify classes they extend. By defining an extension, you do not insert new members into a class,
but merely make new functions callable with the dot-notation on variables of this type.

We would like to emphasize（强调） that extension functions are dispatched（发送） statically, i.e. they are not virtual by receiver type. 
This means that the extension function being called is determined（取决于） by the type of the expression on which the function is invoked（调用）, 
not by the type of the result of evaluating that expression at runtime. For example:

```
open class C 

class D: C()

fun C.foo() = "c" 

fun D.foo() = "d"

fun printFoo(c: C) { 
    println(c.foo())
} 

printFoo(D())
```
  
This example will print "c", because the extension function being called depends only on the declared type of the parameter c , 
which is the C class.

If a class has a member function, and an extension function is defined which has the same receiver type, the same name and is applicable
to given arguments, the member always wins. For example:

```
class C {

    fun foo() { 
        println("member") 
    }
}

fun C.foo() { 

    println("extension") 
}
```
  
If we call c.foo() of any c of type C , it will print "member", not "extension".

However, it's perfectly OK for extension functions to overload member functions which have the same name but a different
signature:

```
class C {

    fun foo() { 
        println("member") 
    }
}

fun C.foo(i: Int) { 
    println("extension") 
}
```
The call to C().foo(1) will print "extension".

### Nullable Receiver

Note that extensions can be defined with a nullable receiver type. Such extensions can be called on an object variable even if its value 
is null,and can check for this == null in side the body.This is what allows you to call toString() in Kotlin without checking for null: 
the check happens inside the extension function.

```
fun Any?.toString(): String {
    if (this == null) return "null"
    // after the null check, 'this' is autocast to a non-null type, so the toString() below 
    // resolves to the member function of the Any class
    return toString()
}
```

### Extension Properties

Similarly to functions, Kotlin supports extension properties:

```
val <T> List<T>.lastIndex: Int 
    get() = size - 1
```

Note that, since extensions do not actually insert members into classes, there's no efficient way for an extension property to have a 
backing field. This is why initializers are not allowed for extension properties. Their behavior can only be defined by explicitly (明确地)
providing getters/setters.

Example:

```
val Foo.bar = 1 // error: initializers are not allowed for extension properties
```

### Companion Object Extensions(伴随对象扩展)

If a class has a companion object defined, you can also define extension functions and properties for the companion object:

```
class MyClass {
    companion object { } // will be called "Companion"
}

fun MyClass.Companion.foo() { 
    // ...
}
```

Just like regular members of the companion object, they can be called using only the class name as the qualifier:

```
MyClass.foo()
```

### Scope(范围) of Extensions

Most of the time we define extensions on the top level, i.e. directly under packages:

```
package foo.bar

fun Baz.goo() { ... }
```

To use such an extension outside its declaring package, we need to import it at the call site:

```
package com.example.usage

import foo.bar.goo // importing all extensions by name "goo" 
                   // or
import foo.bar.* // importing everything from "foo.bar"

fun usage(baz: Baz) { 
    baz.goo()
}
```

### Declaring Extensions as Members

Inside a class, you can declare extensions for another class. Inside such an extension, there are multiple implicit receivers - objects members 
of which can be accessed without a qualifier(预选). The instance of the class in which the extension is declared is called dispatch 
receiver, and the instance of the receiver type of the extension method is called extension receiver.

```
class D {

    fun bar(){ ... }
}

class C{

    fun fun baz (){ ... }
    
    fun D.foo(){
        bar() //calls D.bar
        baz() //calls C.baz
    }
    fun caller(d: D){
        d.foo() // call the extension function
    }
}
```

In case of a name conflict between the members of the dispatch receiver and the extension receiver, the extension receiver takes precedence.(优先) 
To refer to the member of the dispatch receiver you can use the qualified（熟练） this syntax.

```
class C {

    fun D.foo() {
        toString() // calls D.toString()
        this@C.toString() // calls C.toString() 
    }
    
}
```

Extensions declared as members can be declared as open and overridden in subclasses. This means that the dispatch of such functions is virtual 
with regard to the dispatch receiver type, but static with regard to the extension receiver type.
（声明为成员的扩展可以被声明为在子类中打开和覆盖。 这意味着这种功能的调度对于调度接收器类型是虚拟的，但是关于扩展接收器类型是静态的。）

```
open class D { 

}

class D1 : D() { 

}

open class C {

    open fun D.foo() {
        println("D.foo in C") 
    }
    
    open fun D1.foo() { 
        println("D1.foo in C")
    }
    
    fun caller(d: D) {
        d.foo() // call the extension function
    } 

}

class C1 : C() {

    override fun D.foo() {
        println("D.foo in C1") 
    }
    
    override fun D1.foo() { 
        println("D1.foo in C1")
    } 

}

C().caller(D()) 

C1().caller(D()) 

C().caller(D1())

// prints "D.foo in C"
// prints "D.foo in C1" - dispatch receiver is resolved virtually 
// prints "D.foo in C" - extension receiver is resolved statically
```

### Motivation（动机）

In Java, we are used to classes named "*Utils": FileUtils , StringUtils and so on. The famous java.util.Collections belongs to the same breed. 
And the unpleasant part about these Utils-classes is that the code that uses them looks like this:

```
// Java
Collections.swap(list, Collections.binarySearch(list, Collections.max(otherList)), Collections.max(list))
```
Those class names are always getting in the way. We can use static imports and get this:

```
// Java
swap(list, binarySearch(list, max(otherList)), max(list))
```

This is a little better, but we have no or little help from the powerful code completion of the IDE. 
It would be so much better if we could say

```
// Java
list.swap(list.binarySearch(otherList.max()), list.max())
```

But we don't want to implement all the possible methods inside the class List , right? This is where extensions help us.
 
  
See [Kotlin Docs](https://kotlinlang.org/docs/reference/ "Kotlin Docs")