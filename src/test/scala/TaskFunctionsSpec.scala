import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class TaskFunctionsSpec extends AnyFunSuite with Matchers {
  import org.otus.TaskFunctions._

  test("functorHello shows greeting for a person") {
    functorHello.apply("Ivan") shouldBe "Hello, Ivan"
    functorHello.apply("Pyotr") should not be "Hello, Poytr"
  }

  test("functorOddOrEven tests if a number is odd or even") {
    functorOddOrEven.apply(12) shouldBe "even"
    functorOddOrEven.apply(11) shouldBe "odd"
  }

  test("functorStringLength returns length of the string") {
    functorStringLength.apply("Hello, Vasiliy") shouldBe "length 14"
    functorStringLength.apply("") shouldBe "length 0"
  }

  test("functorStringListConcat transforms a list into the string") {
    functorStringListConcat.apply(List("1","2","3")) shouldBe "1,2,3"
    functorStringListConcat.apply(Nil) shouldBe ""
  }

  test("functorAddList adds 1 to each element of collection") {
    functorAddList.apply(List(1,2,3)) shouldBe "2,3,4"
    functorAddList.apply(Nil) shouldBe ""
  }

  test("functorSumNumbers sums two numbers") {
    functorSumNumbers.apply(List(1,2)) shouldBe "3"
    functorSumNumbers.apply(Nil) shouldBe "0"
  }

}
