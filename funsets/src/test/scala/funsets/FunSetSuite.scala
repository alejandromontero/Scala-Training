package funsets

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  trait QueryTests {
    val onetothree = union(union(singletonSet(1), singletonSet(2)), singletonSet(3))
    val positives = union(singletonSet(0), singletonSet(100))
    val negatives = union(singletonSet(-100), singletonSet(0))
    val positivesandnegatives = union(singletonSet(-100), singletonSet(100))
  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val u = union(s1, s2)
      val u2 = union(union(s1, s2), s3)
      assert(contains(u, 1), "Union 1")
      assert(contains(u, 2), "Union 2")
      assert(!contains(u, 3), "Union 3")
      assert(contains(u2, 3), "Union 4")
    }
  }

  test("intersect contains elements from both sets") {
    new TestSets {
      val u = union(s1, s2)
      val u2 = union(union(s1, s2), s3)
      val u3 = intersect(u, u2)
      assert(contains(u3, 1), "Intersect 1")
      assert(contains(u3, 2), "Intersect 2")
      assert(!contains(u3, 3), "Intersect 3")
    }
  }

  test("difference contains the elements of one set not present in the other") {
    new TestSets {
      val d = union(union(s1, s2), s3)
      val d2 = union(s1, s2)
      val d3 = diff(d, d2)
      assert(contains(d3, 3), "Difference must contain a 3")
      assert(!contains(d3, 1), "Difference should NOT contain a 1")
      assert(!contains(d3, 2), "Difference should NOT contain a 2")
    }
  }

  test("filter s in function of p") {
    new TestSets {
      val f = union(union(s1, s2), s3)
      val f2 = filter(f, x => x < 2)
      assert(contains(f2, 1), "Filter should contain a 1")
      assert(!contains(f2, 2), "Filter should contain a 2")
      assert(!contains(f2, 3), "Filter should NOT contain a 3")
      /*Test 2*/
      val f3 = filter(f, x => x == 2)
      assert(contains(f3, 2), "Second filter should contain a 2")
      assert(!contains(f3, 1), "Second filter should NOT contain a 1")
    }
  }

  test("return if all elements of a set satisfy a condition") {
    new QueryTests {
      assert(forall(onetothree, x => x <= 100), "All elements should be lower than 100")
      assert(forall(positives, x => x >= 0), "All elements should be positive")
      assert(forall(negatives, x => x <= 0), "All elements should be negative")
      assert(!forall(positivesandnegatives, x => x >= 0), "If negative numbers in the set should fail")
    }
  }

  test("return if at least one element of a set satisfies a condition") {
    new QueryTests {
      assert(exists(onetothree, x => x == 2), "All elements should be lower than 100")
      assert(exists(positives, x => x > 0), "At least one positive")
      assert(exists(negatives, x => x < 0), "At least one negative")
      assert(exists(positivesandnegatives, x => x >= 0), "At least one positive")
      assert(!exists(negatives, x => x > 0), "if not a single positive, fail")
      assert(!exists(positives, x => x < 0), "if not a single negative, fail")
    }
  }

  test("transform all elements of a set"){
    new QueryTests {
      assert(contains(map(onetothree,x => x*2),6),"3x2 should be on the set")
      assert(!contains(map(onetothree,x => x*2),1),"1 should NOT be on the set")
      assert(exists(map(positives,x => -x),x => x < 0),"positives should be converted to negatives")
    }
  }
}
