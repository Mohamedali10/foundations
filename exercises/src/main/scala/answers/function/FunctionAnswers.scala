package answers.function

import exercises.function.FunctionExercises.{Person, updateAge}
import toimpl.function.FunctionToImpl

import scala.annotation.tailrec
import scala.collection.mutable

object FunctionAnswers extends FunctionToImpl {

  def apply[A, B](f: A => B, value: A): B =
    f(value)

  def identity[A](x: A): A = x

  def const[A, B](a: A)(b: B): A = a

  def tripleAge(xs: List[Person]): List[Person] =
    updateAge(xs, _ * 3)

  def setAge(xs: List[Person], value: Int): List[Person] =
    updateAge(xs, const(value))

  def noopAge(xs: List[Person]): List[Person] =
    updateAge(xs, identity)

  def updateAge2(f: Int => Int): List[Person] => List[Person] =
    xs => updateAge(xs, f)

  def setAge2(value: Int): List[Person] => List[Person] =
    updateAge2(const(value))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    a => g(f(a))

  def compose[A, B, C](f: B => C, g: A => B): A => C =
    a => f(g(a))

  val inc   : Int => Int = x => x + 1
  val double: Int => Int = x => 2 * x

  val doubleInc: Int => Int = andThen(double, inc)

  def curry[A, B, C](f: (A, B) => C): A => B => C =
    a => b => f(a, b)

  def uncurry[A, B, C](f: A => B => C): (A, B) => C =
    (a, b) => f(a)(b)

  def join[A, B, C, D](f: A => B, g: A => C)(h: (B, C) => D): A => D =
    a => h(f(a), g(a))

  def sumList(xs: List[Int]): Int = {
    @tailrec
    def _sumList(ys: List[Int], acc: Int): Int =
      ys match {
        case Nil    => acc
        case h :: t => _sumList(t, acc + h)
      }

    _sumList(xs, 0)
  }

  def memoize[A, B](f: A => B): A => B = {
    val cache = mutable.Map.empty[A, B]
    (a: A) => {
      cache.get(a) match {
        case Some(b) => b    // cache succeeds
        case None    =>
          val b = f(a)
          cache.update(a, b) // update cache
          b
      }
    }
  }
}
