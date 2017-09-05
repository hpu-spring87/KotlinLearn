package me.chunsheng.kotlinlearn

/**
 * Copyright © 2016 edaixi. All Rights Reserved.
 * Author: wei_spring
 * Date: 2017/8/29
 * Email:weichsh@edaixi.com
 * Function:
 */

data class Person(val name: String,
                  val age: Int? = null)

fun main(args: Array<String>) {

    val persons = listOf(Person("Alice"),
            Person("Bob", age = 29))

    val oldest = persons.maxBy { it.age ?: 0 }

    println("The oldest is: $oldest")
}
