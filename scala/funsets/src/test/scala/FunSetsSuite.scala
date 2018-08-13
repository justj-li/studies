package funsets
//This file is part of package 'funsets'


/**
  * Here, we use the 'FunSuite' style trait of ScalaTest. Other style traits that
  * could be used are described in
  * [ http://www.scalatest.org/user_guide/selecting_a_style ]
  *
  * For a complete tutorial on FunSuit visit
  * [ doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite ]
  */
import org.scalatest.FunSuite

/**
  * To run the test suit with eclipse, using the built-in JUnit test runner,
  * add the the following imports and annotation:
  *
  *import org.junit.runner.RunWith
  *import org.scalatest.junit.JUnitRunner
  *@RunWith(classOf[JUnitRunner])
  *
  */





class FunSetsSuite extends FunSuite {

  import FunSets._

  /**
    * Import the classes, objects and methods that will be tested
    */

  /**
    * Create a trait for data that can be reused in tests.
    * Notice that in creation of data might use methods that will be tested
    * later on, however at this point the only requirement is that the methods
    * have been implemented (or at least, have a type).
    */
  trait TestSets {
    val s1: Set   = singletonSet(1)
    val s2: Set   = singletonSet(2)
    val s3: Set   = singletonSet(3)
    val s123: Set = union(union(s1,s2),s3)

    def even: Int => Boolean = x => x%2 == 0
  }

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When a test uses the data created in the data trait then make an instance
    * of the trait gives access to data.
    */
  test("singletonSet(1) is a singleton") {
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(!empty(s1), "Singleton is not empty") //only for bounded sets
      assert(empty(filter(s1,x=>x!=1)), "Singleton has only one element")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      assert(contains(s123, 1), "Union 1")
      assert(contains(s123, 2), "Union 2")
      assert(!contains(s123, 3) === false, "Union 3")
    }
  }

  test("intersection contains only common elements") {
    new TestSets {
      assert(contains(intersect(s123, s1), 1), "Intersection is singleton 1")
    }
  }

  test("difference contains a relative complement") {
    new TestSets {
      val t: Set = diff(s123, s1)
      assert(!contains(t, 1), "Difference contains not 1")
      assert(contains(t, 2), "Difference contains 2")
      assert(contains(t, 3), "Difference contains 3")
    }
  }

  test("filter contains a subset of elements") {
    new TestSets {
      val t: Set = filter(s123, x => x%2==1)
      assert(contains(t, 1), "Filters in 1 because it is odd")
      assert(!contains(t, 2), "Filters out 2 because it is not odd")
      assert(contains(t, 3), "Filters in 3 because it is odd")
    }
  }

  test("forall tests whether a predicate is true on all elements") {
    new TestSets {
      assert(!forall(s123, even), "Not all are even numbers")
      assert(forall(s123, x => x > 0), "All numbers are positive")
    }
  }

  test("exists tests whether a predicate is true on some elements") {
    new TestSets {
      assert(exists(s123, even), "Exists an even number")
      assert(!exists(s123, x => x < 0), "No negative numbers")
    }
  }


  test("map applies a function to all elements") {
    new TestSets {
      val sqrs: Set = map(s123, x => x*x)
      assert(contains(sqrs,1), "Mapping 1 to 1")
      assert(contains(sqrs,4), "Mapping 2 to 4")
      assert(contains(sqrs,9), "Mapping 3 to 9")
    }
  }


}
