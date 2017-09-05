## Object Expressions and Declarations

> Sometimes we need to create an object of a slight modification of some class, without explicitly declaring a new subclass for it. 
  Java handles this case with anonymous inner classes. Kotlin slightly generalizes this concept with object expressions and object 
  declarations.
  (有时候，我们需要创建一个很少修改某个类的对象，而不需要明确地声明一个新的子类。 Java使用匿名内部类来处理这种情况。 Kotlin用对象表达式和对象声明略微概括了这个概念。)
  
### Object expressions

To create an object of an anonymous class that inherits from some type (or types), we write:

```
window.addMouseListener(object : MouseAdapter() { 

    override fun mouseClicked(e: MouseEvent) {
        // ...
    }
    
    override fun mouseEntered(e: MouseEvent) { 
        // ...
    } 
    
})
```

If a supertype has a constructor, appropriate constructor parameters must be passed to it. Many supertypes may be specified as a 
comma-separated list after the colon（冒号）:

```
open class A(x: Int) {

    public open val y: Int = x
    
}

interface B {
    ...
}

val ab: A = object : A(1), B { 
    override val y = 15
}

```

If, by any chance, we need "just an object", with no nontrivial supertypes, we can simply say:

```
fun foo() {

    val adHoc = object {
        var x: Int = 0
        var y: Int = 0 
    }
    
    print(adHoc.x + adHoc.y) 
}
```

Note that anonymous objects can be used as types only in local and private declarations. If you use an anonymous object as a 
return type of a public function or the type of a public property, the actual type of that function or property will be the 
declared supertype of the anonymous object, or Any if you didn't declare any supertype. Members added in the anonymous object 
will not be accessible.
（请注意，匿名对象可以仅用作本地和私有声明中的类型。 如果使用匿名对象作为公共函数的返回类型或公共属性的类型，则该函数或属性的实际类型将是匿名对象
  的声明的超类型，如果您没有声明任何超类型。 在匿名对象中添加的成员将无法访问。）
  
```
class C {

    // Private function, so the return type is the anonymous object type 
    private fun foo() = object {
        val x: String = "x" 
    }

    // Public function, so the return type is Any
    fun publicFoo() = object { 
        val x: String = "x"
    }
    
    fun bar() {
        //由于该函数是私有声明，所以可以访问匿名对象里面声明的字段
        val x1 = foo().x // Works
        val x2 = publicFoo().x // ERROR: Unresolved reference 'x'，
        //因为该公共函数返回值是匿名对象，由于匿名对象没有定义超类，那么在匿名对象里面定义的成员x将无法访问
    } 
    
}
```

Just like Java's anonymous inner classes, code in object expressions can access variables from the enclosing scope. 
(Unlike Java, this is not restricted(限制) to final variables.)

```
fun countClicks(window: JComponent) { 

    var clickCount = 0
    var enterCount = 0
    window.addMouseListener(object : MouseAdapter() { 
    
            override fun mouseClicked(e: MouseEvent) {
                clickCount++ 
            }
        
            override fun mouseEntered(e: MouseEvent) { 
                enterCount++
            } 
    })
// ...
}
```

### Object declarations

Singleton is a very useful pattern, and Kotlin (after Scala) makes it easy to declare singletons:

```Kotlin
object DataProviderManager {

    fun registerDataProvider(provider: DataProvider) {
        // ...
    }

    val allDataProviders: Collection<DataProvider> 
        get() = // ...
    
}
```

This is called an object declaration, and it always has a name following the object keyword. Just like a variable declaration, 
an object declaration is not an expression, and cannot be used on the right hand side of an assignment statement.

To refer to the object, we use its name directly:

```
DataProviderManager.registerDataProvider(...)
```

Such objects can have supertypes:

```
object DefaultListener : MouseAdapter() { 

    override fun mouseClicked(e: MouseEvent) {
        // ...
    }
    
    override fun mouseEntered(e: MouseEvent) { 
        // ...
    } 

}
```

**NOTE:** object declarations can't be local (i.e. be nested(嵌套) directly inside a function), but they can be nested into other object 
declarations or non-inner classes.

**Companion Objects（伴随对象）**

An object declaration inside a class can be marked with the companion keyword:

```
class MyClass {

    companion object Factory {
        fun create(): MyClass = MyClass() 
    }
    
}
```

Members of the companion object can be called by using simply the class name as the qualifier:

```
val instance = MyClass.create()
```

The name of the companion object can be omitted, in which case the name Companion will be used:

```
class MyClass { 

    companion object { }
    
}

val x = MyClass.Companion
```

Note that, even though the members of companion objects look like static members in other languages, 
at runtime those are still instance members of real objects, and can implement interfaces for example:

```
interface Factory<T> { 
    fun create(): T
}

class MyClass {

    companion object : Factory<MyClass> {
        override fun create(): MyClass = MyClass() 
    }
}
```

However, on the JVM you can have members of companion objects generated as real static methods and fields, if you use the 
@JvmStatic annotation. See the Java interoperability（互通性） section for more details.

**Semantic difference between object expressions and declarations(对象表达式和声明之间的语义差异)**

There is one important semantic difference between object expressions and object declarations:

* — object expressions are executed (and initialized) immediately, where they are used
* — object declarations are initialized lazily, when accessed for the first time
* — a companion object is initialized when the corresponding(相应) class is loaded (resolved), matching the semantics of a Java static initializer

See [Kotlin Docs](https://kotlinlang.org/docs/reference/ "Kotlin Docs")










