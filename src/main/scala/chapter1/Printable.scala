package chapter1

/**
  *
  * @author 许斌 ,mark.xub@alibaba-inc.com
  * @date 2018/06/07
  */


trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val stringPrintable = new Printable[String] {
    override def format(value: String) = value
  }

  implicit val intPrintable = new Printable[Int] {
    override def format(value: Int) = value.toString
  }

  implicit val printableCat = new Printable[Cat] {
    override def format(cat: Cat) = {
      val name = Printable.format(cat.name)
      val age = Printable.format(cat.age)
      val color = Printable.format(cat.color)
      s"$name is a $age year-old $color cat."
    }
  }
}

object Printable {
  def format[A](input: A)(implicit p: Printable[A]): String =
    p.format(input)

  def print[A](input: A)(implicit p: Printable[A]): Unit =
    println(format(input))
}

object PrintableSyntax {

  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String =
      p.format(value)

    def print(implicit p: Printable[A]):Unit =
      println(format(p))
  }

}

object print extends App {
  import PrintableInstances._
  import PrintableSyntax._
  Printable.print(Cat("xx", 1, "red"))
  Cat("dd", 2, "blue").print
}

