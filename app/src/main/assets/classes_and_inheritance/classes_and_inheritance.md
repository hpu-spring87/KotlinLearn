## Classes and Inheritance

### Classes

> Classes in Kotlin are declared using the keyword class:

```
 class Invoice { 
 }
```

The class declaration consists of the class name, the class header (specifying its type parameters, the primary constructor etc.) 
and the class body, surrounded by curly braces. Both the header and the body are optional; if the class has no body, curly braces can 
be omitted.
(类的标题和内容都是可选的，如果类没有内容，花括号是可以省略的)

```
class Empty
```

**Constructors**

A class in Kotlin can have a primary constructor and one or more secondary constructors. The primary constructor is part of the class
header: it goes after the class name (and optional type parameters).

```
class Person constructor(firstName: String) { }
```

If the primary constructor does not have any annotations or visibility modifiers, the constructor keyword can be omitted:

```
class Person(firstName: String) { }
```

The primary constructor cannot contain any code. Initialization code can be placed in initializer blocks, 
which are prefixed with the init keyword:
（主构造函数不能包含任何代码。 初始化代码可以放在初始化程序块中，前缀为init关键字：）

```
class Customer(name: String) { 
    init {
        logger.info("Customer initialized with value ${name}") 
    }
}
```

Note that parameters of the primary constructor can be used in the initializer blocks. They can also be used in property 
initializers declared in the class body:

```
class Customer(name: String) {
    val customerKey = name.toUpperCase()
}
```

In fact, for declaring properties and initializing them from the primary constructor, Kotlin has a concise syntax:（简介的语法）

```
class Person(val firstName: String, val lastName: String, var age: Int) {
    // ...
}
```

Much the same way as regular properties, the properties declared in the primary constructor can be mutable (var) or read-only (val).

If the constructor has annotations or visibility modifiers, the constructor keyword is required, and the modifiers go before it:
（如果构造函数具有注解或可见性修饰符，那么构造函数关键字是必需的，修饰符就在它之前：）

```
class Customer public @Inject constructor(name: String) { ... }
```

Secondary Constructors

The class can also declare secondary constructors, which are prefixed with constructor:
```
class Person { 
    constructor(parent: Person) {
        parent.children.add(this) 
    }
}
```

If the class has a primary constructor, each secondary constructor needs to delegate to the primary constructor, either directly or 
indirectly through another secondary constructor(s). Delegation to another constructor of the same class is done using the this keyword:
（如果类具有主构造函数，则每个辅助构造函数需要通过另一个辅助构造函数直接或间接地委派给主构造函数。 使用此关键字对同一类的另一个构造函数进行委派：）
```
class Person(val name: String) {
    constructor(name: String, parent: Person) : this(name) {
        parent.children.add(this)
    }
}
```

If a non-abstract class does not declare any constructors (primary or secondary), it will have a generated primary constructor with 
no arguments. The visibility of the constructor will be public. If you do not want your class to have a public constructor, you need 
to declare an empty primary constructor with non-default visibility:
（如果一个非抽象类没有声明任何构造函数（primary或secondary），那么它将具有没有参数的生成的主构造函数。 构造函数的可见性将是公开的。 
如果您不希望您的类具有公共构造函数，则需要声明具有非默认可见性的空主构造函数：）

```
 class DontCreateMe private constructor () { 
 
 }
```

> **NOTE:** On the JVM, if all of the parameters of the primary constructor have default values, the compiler will generate an 
  additional parameterless constructor which will use the default values. This makes it easier to use Kotlin with libraries such 
  as Jackson or JPA that create class instances through parameterless constructors. class Customer(val customerName: String = "")
  
**Creating instances of classes**

To create an instance of a class, we call the constructor as if it were a regular function:

```
val invoice = Invoice()
val customer = Customer("Joe Smith")
```

Note that Kotlin does not have a new keyword.

Creating instances of nested, inner and anonymous inner classes is described in Nested classes.

**Class Members**

Classes can contain

* — Constructors and initializer blocks 
* — Functions
* — Properties
* — Nested and Inner Classes
* — Object Declarations

### Inheritance

All classes in Kotlin have a common super class Any,that is a default super for a class with no super types declared:

```
class Example // Implicitly inherits from Any
```

Any is not java.lang.Object ; in particular, it does not have any members other than equals() , hashCode() and toString() . 
Please consult the Java interoperability section for more details.

To declare an explicit supertype, we place the type after a colon(冒号) in the class header:

```
open class Base(p: Int)

class Derived(p: Int) : Base(p)
```

If the class has a primary constructor, the base type can (and must) be initialized right there, using the parameters of 
the primary constructor.

If the class has no primary constructor, then each secondary constructor has to initialize the base type using the super keyword,
or to delegate to another constructor which does that. Note that in this case different secondary constructors can call different 
constructors of the base type:
（如果类没有主构造函数，则每个辅助构造函数必须使用super关键字初始化基类型，或者委托给另一个构造函数。 
请注意，在这种情况下，不同的辅助构造函数可以调用基类型的不同构造函数：）

```
class MyView : View {

    constructor(ctx: Context) : super(ctx)
    
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) 
    
}
```

The open annotation on a class is the opposite of Java's final: it allows others to inherit from this class. By default, 
all classes in Kotlin are final, which corresponds to Effective Java, Item 17: Design and document for inheritance or else prohibit it.

**Overriding Methods**

As we mentioned before, we stick to making things explicit in Kotlin. And unlike Java, Kotlin requires explicit annotations 
for overridable members (we call them open) and for overrides:

```
open class Base { 
    open fun v() {}
    fun nv() {} 
}

class Derived() : Base() { 
    override fun v() {}
}
```

The override annotation is required for Derived.v() . If it were missing, the compiler would complain. If there is no open annotation 
on a function, like Base.nv() , declaring a method with the same signature in a subclass is illegal, either with override or without it. 
In a final class (e.g. a class with no open annotation), open members are prohibited.（被禁止）

A member marked override is itself open, i.e. it may be overridden in subclasses. If you want to prohibit re-overriding（禁止覆盖）, use final:

```
open class AnotherDerived() : Base() { 
    final override fun v() {}
}
```

**Overriding Properties**

Overriding properties works in a similar way to overriding methods; properties declared on a superclass that are then 
redeclared on a derived class must be prefaced with override, and they must have a compatible type. Each declared property 
can be overridden by a property with an initializer or by a property with a getter method.

```
open class Foo {
    open val x: Int get { ... }
}

class Bar1 : Foo() {
    override val x: Int = ...
}
```

You can also override a val property with a var property, but not vice versa（但是不可逆）. This is allowed because a val property essentially (实质上)
declares a getter method, and overriding it as a var additionally declares a setter method in the derived class.

Note that you can use the override keyword as part of the property declaration in a primary constructor.

```
interface Foo { 
    val count: Int
}

class Bar1(override val count: Int) : Foo

class Bar2 : Foo {
    override var count: Int = 0
}
```

**Overriding Rules**

> In Kotlin, implementation inheritance(继承) is regulated by the following rule: if a class inherits many implementations of the same 
member from its immediate superclasses, it must override this member and provide its own implementation (perhaps, using one of 
the inherited ones). To denote the supertype from which the inherited implementation is taken, we use super qualified by the 
supertype name in angle brackets, e.g. super<Base> :

```
open class A {
    open fun f() { print("A") } 
    fun a() { print("a") }
}

interface B {
    fun f() { print("B") } // interface members are 'open' by default 
    fun b() { print("b") }
}

class C() : A(), B {
    // The compiler requires f() to be overridden: 
    override fun f() {
        super<A>.f() // call to A.f()
        super<B>.f() // call to B.f() 
    }
}
```

It's fine to inherit from both A and B , and we have no problems with a() and b() since C inherits only one implementation of each of 
these functions. But for f() we have two implementations inherited by C , and thus we have to override f() in C and provide our own 
implementation that eliminates the ambiguity.

### Abstract Classes


A class and some of its members may be declared abstract. An abstract member does not have an implementation in its class. 
Note that we do not need to annotate an abstract class or function with open – it goes without saying.
We can override a non-abstract open member with an abstract one

```
open class Base { 
    open fun f() {}
}

abstract class Derived : Base() { 
    override abstract fun f()
}
```

### Companion Objects

> In Kotlin, unlike Java or C#, classes do not have static methods. In most cases, it's recommended to simply use package-level functions instead.

If you need to write a function that can be called without having a class instance but needs access to the internals of a class (for example, a factory method), 
you can write it as a member of an object declaration inside that class.

Even more specifically, if you declare a companion object inside your class, you'll be able to call its members with the same 
syntax as calling static methods in Java/C#, using only the class name as a qualifier.


See [Kotlin Docs](https://kotlinlang.org/docs/reference/ "Kotlin Docs")