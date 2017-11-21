import week3._

def nth[T](xs: List[T], index: Int): T = {
  if (xs.isEmpty) throw new IndexOutOfBoundsException
  else if (index == 0) xs.head
  else nth(xs.tail,index-1)
}

val list = new Cons(1,new Cons(2, new Cons(3, new Nil)))
nth(list, 2)
nth(list,-1)
nth(list,-5)