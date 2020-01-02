package ru.itis.demo

import ru.itis.Client

var amount = 0;
/*class ru.itis.demo.SomeClass {
    var a: Int
    var b: String
    var count: Int

    constructor(a: Int, b: String) {
        this.a = a
        this.b = b
        this.count = ru.itis.getAmount++
    }

    fun start() {
        println("A: $a; B:$b; COUNT:$count")
    }
}*/

class SomeClass(var a: Int, var b: String) {
    var count: Int

    init {
        this.count = amount++
    }

    fun start() {
        println("A: $a; B:$b; COUNT:$count")
    }
}

fun main() {
    val sc1 = SomeClass(1, "foo")
    val sc2 = SomeClass(13, "bar")
    val sc3 = SomeClass(20, "latop")
    sc1.start()
    sc2.start()
    sc3.start()
    SomeClass(15, "fokar").start()
    val data = object {
        val key = 13
        val value = 145
    }
    print(data.key)
    print(data.value)
//    Client().run()

}



//class PersonJava {
//    val name: String
//    val age: Int?
//
//    constructor(name: String) {
//        this.name = name
//        age = null
//    }
//}