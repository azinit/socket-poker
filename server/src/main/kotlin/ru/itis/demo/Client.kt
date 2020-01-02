package ru.itis

import org.json.JSONObject
import java.net.*
import java.io.*

class Client: Runnable {
    val SUCCESS_CODE = 200
    val EXIT_CODE = 100
    val DEFAULT_CODE = 400

    override fun run() {
        val socket = Socket("127.0.0.1", 8080)
        val writer = PrintWriter(OutputStreamWriter(socket.getOutputStream()))
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

        while (true) {
            val action: String = getUserAction()
            val data: JSONObject = getUserData(action)
            val request = JSONObject(object {
                val action = action
                val data = data
            })
            writer.println(request)
            writer.flush()

            val response = JSONObject(reader.readLine() as String)
            val code = dispatch(response)
            if (code == EXIT_CODE) {
                break
            }
        }
        writer.close()
        reader.close()
    }

    private fun getUserAction(): String {
        println("APP: Choose action")
        println("1. Random card")
        println("2. Get data")
        println("3. Get total data")
//        println("2. Factorial")
//        println("3. Fibonacci")
        println("4. Exit")
        println("5. <Unknown operation>")
        print(">>> ")
        val response = readLine()
        return when(response) {
            "1" -> "get_random_card"
            "2" -> "get_data"
            "3" -> "get_total_data"
//            "2" -> "factorial"
//            "3" -> "fib"
            "4" -> "exit"
            "5" -> "unknown"
            ""  -> getUserAction()
            else -> getUserAction()
        }
    }

    private fun getUserData(action: String): JSONObject {
        val obj = JSONObject(object{})
        println("APP > ${action.toUpperCase()}")
        when (action) {
//            "sum" -> {
//                println("APP > SUM: Type two operands for sum")
//                print(">>> ")
//                obj.put("op1", readLine()?.toInt())
//                obj.put("op2", readLine()?.toInt())
//            }
//            "factorial" -> {
//                println("APP > FACTORIAL: Type power of factorial number [n!]")
//                print(">>> ")
//                obj.put("n", readLine()?.toInt())
//            }
//            "fib" -> {
//                println("APP > FIB: Type power of fib number [fib(n)]")
//                print(">>> ")
//                obj.put("n", readLine()?.toInt())
//            }
        }

        return obj
    }

    private fun dispatch(response: JSONObject): Int {
        val result = response.get("data")
        val code = response.get("code") as Int

        when (code) {
            SUCCESS_CODE -> println("<<< $result")
            DEFAULT_CODE -> println("<<< Operation not found...")
            EXIT_CODE    -> println("<<< Exit from app...")
        }

        return code
    }


}

fun main() {
    Client().run()
}