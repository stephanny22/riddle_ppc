package com.datoban.kotlin_example

fun main() {
//Primer punto
    /*println("Please insert the first value")
    val first_number = readln().toInt()
    println("Please insert the second value")
    val second_number = readln().toInt()
    println("Please insert the third value")
    val third_number = readln().toInt()
    val average = ((first_number+second_number+third_number)/3)
    print("the average result is: " + average)*/

//Segundo punto
    /*Use the when operator to calculate whether a value requested
     by the user is in the range 1 to 50 to print Low, range 51 to
     100 to print Medium, and after 100, print High.*/
    println("Please insert a value to classificate it")
    val value = readln().toInt()
    when {
        value in 1..50->print("Low :(")
        value in 51..100->print("Medium :)")
        value > 100 ->print("High :O")
    }
}