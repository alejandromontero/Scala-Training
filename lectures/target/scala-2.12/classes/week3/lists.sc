import week3._

val list = new Cons(1,new Cons(2, new Cons(3, new Nil)))
list.nth(list,2)
list.nth(list,-1)