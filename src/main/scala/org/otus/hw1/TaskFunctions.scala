package org.otus.hw1

import InputProcessor._

trait TaskFunctions {
  val functorHello: String => String = (x: String) => s"Hello, ${x}"
  val functorOddOrEven: Int => String = (x: Int) => if (x % 2 == 0) "even" else "odd"
  val functorStringLength: String => String = (x: String) => s"length ${x.length}"

  val functorStringListConcat: List[String] => String = x => x.mkString(",")
  val functorAddList: List[Int] => String = (x: List[Int]) => x.map(_ + 1).mkString(",")
  val functorSumNumbers: List[Int] => String = (x: List[Int]) => x.sum.toString
}

object TaskFunctions extends App with TaskFunctions {

//  Создайте программу, которая принимает строку и выводит её длину.
    println("Length of the string. One attempt")
    inputAndProcessForSingleValue[String](functor = functorStringLength)

  //  Напишите программу, которая принимает число с клавиатуры и выводит, является ли оно четным или нечетным.
   println("Checks the number if it is edd or even")
   inputAndProcessForSingleValue[Int](functor = functorOddOrEven)

//  Напишите программу на Scala, которая принимает имя пользователя с клавиатуры и выводит приветственное сообщение.
  println("Shows greeting for the user")
  inputAndProcessForSingleValue[String](functor = functorHello)

  //  Напишите функцию, которая принимает список строк и возвращает новую строку, состоящую из всех строк списка, разделенных пробелами.
  println("Concatenates string")
  inputAndProcessForList[String](functor = functorStringListConcat, maxNumbers = 5)

  //  Создайте список из нескольких чисел (например, List(1, 2, 3, 4, 5)) и примените к нему функцию, которая увеличивает каждое число на 1. Выведите получившийся список на экран.
  println("Adds 1 to each element of collection")
  inputAndProcessForList[Int](functor = functorAddList, maxNumbers = 5)

  // Напишите функцию, которая принимает два целых числа и возвращает их сумму.
  println("Sum two numbers")
  inputAndProcessForList[Int](functor = functorSumNumbers, maxNumbers = 2)
}
