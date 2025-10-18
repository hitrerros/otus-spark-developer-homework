package org.otus.hw2


import scala.io.StdIn.readLine

case class User(name: String, age: Int, isStudent: Boolean)

object User {
  def apply: User = {
    print("name:")
    val name: String = readLine
    print("age: ")
    val age: String = readLine
    print("is student Y/N: ")
    val isStudent: String = readLine

    if (name.isEmpty || !age.matches("\\d+") || !isStudent.matches("^[YyNn]$")) {
      println("wrong input, try again")
      apply
    } else User(name, age.toInt, if (isStudent.toLowerCase == "y") true else false)
  }
}


object VariousFunctions extends App {

  // Создайте переменные следующих типов:
  var age: Int = 0
  var weight: Double = 0
  var name: String = ""
  val isStudent: Boolean = false



  // Выведите значения всех переменных на экран с помощью функции println.
  println(s"name: ${name}, age ${age}, weight ${weight}, is student ${isStudent}  ")

  val r = new scala.util.Random

  // Напишите функцию, которая принимает два целых числа и возвращает их сумму.
  val sum2Integers: (Int, Int) => Int = (x, y) => {
    println(s"provided ${x} ${y} "); x + y
  }
  // Вызовите эту функцию с любыми двумя числами и выведите результат на экран.
  println(sum2Integers.apply(r.nextInt(100), r.nextInt(200)))

  // Напишите функцию, которая принимает возраст и возвращает строку "Молодой", если возраст меньше 30, и "Взрослый", если возраст 30 или больше.
  val isYoungOrAdult: Int => String = (x) => {
    println(s"возраст ${x} "); if (x > 30) "Взрослый" else "Молодой"
  }
  // Вызовите эту функцию с вашей переменной возраста и выведите результат на экран.
  println(isYoungOrAdult.apply(r.between(1, 100)))

  // Напишите цикл, который выводит на экран числа от 1 до 10.
  println(Range.inclusive(1, 10).mkString(","))

  // 5.1. Создайте список имен студентов и выведите каждое имя на экран с помощью цикла.
  val student = List("ivanov", "petrov", "sidorov")
  student.foreach(k => print(s"$k "))
  println

  // Напишите программу, которая выполняет следующие действия:
  // Запрашивает у пользователя ввод имени, возраста и статуса (студент или нет).
  // Использует написанные выше функции и выводит на экран информацию о пользователе и его возрастную категорию.
  println("Введите данные пользователя: ")
  val usr = User.apply
  println(s"Поколение ${isYoungOrAdult(usr.age)}" )

 // Изучить и научиться использовать for comprehension в языке программирования Scala для работы с коллекциями и опциями.
 // Создайте список чисел от 1 до 10.
   val src = List.range(1,10)

  //  Используйте for comprehension, чтобы создать новый список, содержащий квадраты чисел из исходного списка.
  val quadrant =  for (a <- src) yield a * a
  println(quadrant.mkString(","))
  //  Используйте for comprehension, чтобы создать новый список, содержащий только четные числа из исходного списка.
  val oddsOny =  for {a <- src;
                      if a%2==0
                      } yield a
  println(oddsOny.mkString(","))
}
