def sum(f: Int => Int, a: Int, b: Int): Int = {
  def loop(a: Int, acc: Int): Int = {
    if (a > b) acc
    else loop(a+1, f(a) + acc)
  }
  loop(a, 0)
}

sum (x => x * x, 3, 5)

def sumInts(a: Int, b: Int) = sum(x => x, a, b)
def sumCubes(a: Int, b: Int) = sum(x => x*x*x, a, b)

sumInts(1,10)
sumCubes(3,5) 