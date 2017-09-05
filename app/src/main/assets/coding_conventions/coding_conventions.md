## Coding Conventions

### Naming Style

If in doubt(疑惑), default to the Java Coding Conventions such as:

* — use of camelCase for names (and avoid underscore in names) — types start with upper case
* — methods and properties start with lower case
* — use 4 space indentation
* — public functions should have documentation such that it appears in Kotlin Doc

### Colon（冒号）

There is a space before colon where colon separates type and supertype and there's no space 
where colon separates instance and type:

```
//两种冒号，一种有空格分开，一种没有空格分开
interface Foo<out T : Any> : Bar { 
    fun foo(a: Int): T
}
```

### Lambdas

In lambda expressions, spaces should be used around the curly braces, as well as around the arrow 
which separates the parameters from the body. 
Whenever possible, a lambda should be passed outside of parentheses.
(在lambda表达式中，应该在大括号周围使用空格，以及将参数与正文分开的箭头。 只要有可能，一个lambda应该被传递到括号外)

```
list.filter { it > 10 }.map { element -> element * 2 }
```

In lambdas which are short and not nested, it's recommended to use the it convention instead of declaring the parameter
explicitly. In nested lambdas with parameters, parameters should be always declared explicitly.
(在短而不嵌套的lambdas中，建议使用它，而不是明确声明参数。 在具有参数的嵌套lambdas中，参数应始终明确声明)

### Class header formatting

Classes with a few arguments can be written in a single line:

```
class Person(id: Int, name: String)
```

Classes with longer headers should be formatted so that each primary constructor argument is in a separate line with 
indentation. Also, the closing parenthesis should be on a new line. If we use inheritance, then the superclass constructor
call or list of implemented interfaces should be located on the same line as the parenthesis:
 
```
class Person( 
    id: Int,
    name: String,
    surname: String
) : Human(id, name) {
    // ...
}
```

For multiple interfaces, the superclass constructor call should be located first and then each interface should be 
located in a different line:
(对于多个接口，超类构造函数调用应首先定位，然后每个接口应位于不同的行中：)

```
class Person( 
    id: Int,
    name: String,
    surname: String 
) : Human(id, name),
    KotlinMaker {
    // ...
}
```

Constructor parameters can use either the regular indent or the continuation indent (double the regular indent).
(构造函数参数可以使用常规缩进或连续缩进（双倍的常规缩进）)

### Unit

If a function returns Unit, the return type should be omitted:

```
fun foo() { // ": Unit" is omitted here }
```

### Functions vs Properties

In some cases functions with no arguments might be interchangeable with read-only properties. 
Although the semantics are similar, there are some stylistic conventions on when to prefer one to another.
(在某些情况下，没有参数的函数可能与只读属性可互换。 虽然语义是相似的，但有一些风格约定在何时更喜欢。)
Prefer a property over a function when the underlying algorithm:
(当基础算法时，优先于一个函数的属性：)

* — does not throw
* — has a O(1) complexity
* — is cheap to calculate (or caсhed on the first run) — returns the same result over invocations



See [Kotlin Docs](https://kotlinlang.org/docs/reference/ "Kotlin Docs")