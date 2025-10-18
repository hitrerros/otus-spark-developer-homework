package org.otus.hw1

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.reflect.ClassTag

object InputProcessor {

  private val intRegExp = "\\d+"
  @tailrec
  def inputAndProcessForList[T](
                          functor: List[T] => String,
                          buff: List[T] = List.empty[T],
                          maxNumbers: Int = 1)(implicit ct: ClassTag[T]): Unit = {
    if (maxNumbers == 0) {
        println(s"result: ${functor(buff)}")
      }
    else {
      print(s"insert value [${maxNumbers}], Q to quit:")
      val curr = readLine()
      curr match {
        case curr if (curr == "Q") | (curr == "q") => println("input aborted")
        case _ =>
           if (ct.runtimeClass==classOf[Int]) {
             val isCorrect = curr.matches(intRegExp)
             inputAndProcessForList(functor,if (isCorrect) buff :+ curr.toInt.asInstanceOf[T] else buff,
                                          if (isCorrect) maxNumbers-1 else maxNumbers)
           } else
             inputAndProcessForList(functor, buff :+ curr.asInstanceOf[T],maxNumbers-1)
      }
    }
  }

  def inputAndProcessForSingleValue[T](functor: T => String )
                                      (implicit ct: ClassTag[T]):  Unit = {
    print("insert value : ")
    val curr = readLine()
    if (ct.runtimeClass==classOf[Int]) {
       println(if (curr.matches(intRegExp)) functor.apply(curr.toInt.asInstanceOf[T]) else "wrong input!")
    }
    else println(functor.apply(curr.asInstanceOf[T]))
  }
}
