package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {

    def lookClosing(rest: List[Char], open: Int): Boolean = {
      rest match {
        case Nil => if (open == 0) true else false
        case char :: tail if (char == '(') => lookClosing(tail, open + 1)
        case char :: tail if (char == ')' && open == 0) => false
        case char :: tail if (char == ')' && open > 0) => lookClosing(tail, open - 1)
        case _ :: tail => lookClosing(tail, open)
      }
    }
    lookClosing(chars, 0)
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {

    def count (money: Int, coins: List [Int],accumulate: Int): Int = {
      if (accumulate == money) 1
      else if (accumulate < money && !coins.isEmpty)
        count (money,coins,accumulate + coins.head) + count (money,coins.tail,accumulate)
      else 0
    }

    count (money,coins,0)
  }
}
