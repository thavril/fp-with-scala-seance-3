package seance3

/**
 * Session 3 - Monads and Higher-Order Functions (Part 1)
 * 
 * MAIN OBJECTIVE: Implement our own List!
 * 
 * By recreating the standard library, you will understand:
 * - How map, flatMap, filter, fold work "under the hood"
 * - Why these functions are so powerful
 * - What a Monad is (without the mathematical jargon)
 * 
 * Instructions:
 * 1. Complete the functions marked with ???
 * 2. Do NOT use var, only val
 * 3. Use recursion (no loops)
 * 4. Run tests with: sbt test
 */

/**
 * Our own implementation of List!
 * 
 * A list is either:
 * - Empty (MyNil)
 * - An element followed by another list (MyCons)
 * 
 * Example: MyCons(1, MyCons(2, MyCons(3, MyNil))) represents [1, 2, 3]
 */
enum MyList[+A]:
  case MyNil
  case MyCons(head: A, tail: MyList[A])
  
  // ============================================
  // Part 1: Basic Operations
  // ============================================
  
  /**
   * Exercise 1.1: isEmpty
   * Returns true if the list is empty (MyNil).
   * 
   * Example: MyNil.isEmpty == true
   *          MyCons(1, MyNil).isEmpty == false
   */
  def isEmpty: Boolean = this match
    case MyNil => true
    case _ => false

  /**
   * Exercise 1.2: length
   * Returns the number of elements in the list.
   * Use recursion!
   * 
   * Example: MyNil.length == 0
   *          MyCons(1, MyCons(2, MyNil)).length == 2
   */
  def length: Int = this match
    case MyNil => 0
    case MyCons(head, tail) => 1 + tail.length
  
  /**
   * Exercise 1.3: headOption
   * Returns Some(first element) or None if the list is empty.
   * 
   * Example: MyCons(1, MyCons(2, MyNil)).headOption == Some(1)
   *          MyNil.headOption == None
   */
  def headOption: Option[A] =
    ???
  
  /**
   * Exercise 1.4: lastOption
   * Returns Some(last element) or None if the list is empty.
   * Use recursion!
   * 
   * Example: MyCons(1, MyCons(2, MyCons(3, MyNil))).lastOption == Some(3)
   */
  def lastOption: Option[A] =
    ???
  
  /**
   * Exercise 1.5: append (++)
   * Concatenates two lists.
   * 
   * Example: MyCons(1, MyCons(2, MyNil)) ++ MyCons(3, MyNil) 
   *          == MyCons(1, MyCons(2, MyCons(3, MyNil)))
   */
  def ++[B >: A](other: MyList[B]): MyList[B] =
    ???
  
  /**
   * Exercise 1.6: reverse
   * Reverses the order of elements.
   * Hint: use an accumulator or ++
   * 
   * Example: MyCons(1, MyCons(2, MyCons(3, MyNil))).reverse
   *          == MyCons(3, MyCons(2, MyCons(1, MyNil)))
   */
  List(1, 2 ,3)
  def reverse: MyList[A] = this match
    case MyNil => MyNil
    case MyCons(head, tail) => tail.reverse ++ MyCons(head, MyNil)


  // ============================================
  // Part 2: map - Transform each element
  // ============================================
  
  /**
   * Exercise 2.1: myMap
   * Applies a function f to EACH element of the list.
   * 
   * IMPORTANT: myMap NEVER changes the structure of the list!
   * - If the list has 3 elements, the result has 3 elements
   * - MyNil stays MyNil
   * 
   * Example: MyCons(1, MyCons(2, MyCons(3, MyNil))).myMap(x => x * 2)
   *          == MyCons(2, MyCons(4, MyCons(6, MyNil)))
   * 
   * @param f the transformation function A => B
   * @return a new list with the transformed elements
   */
  def myMap[B](f: A => B): MyList[B] =
    ???
  
  /**
   * Exercise 2.2: Using myMap
   * Use myMap to double all elements of an integer list.
   * (This function is in the companion object below)
   */
  
  
  // ============================================
  // Part 3: filter - Keep certain elements
  // ============================================
  
  /**
   * Exercise 3.1: myFilter
   * Keeps only elements that satisfy the predicate p.
   * 
   * Example: MyCons(1, MyCons(2, MyCons(3, MyCons(4, MyNil)))).myFilter(x => x % 2 == 0)
   *          == MyCons(2, MyCons(4, MyNil))
   * 
   * @param p the predicate (function that returns Boolean)
   * @return a new list with only elements where p(element) == true
   */
  def myFilter(p: A => Boolean): MyList[A] =
    ???
  
  /**
   * Exercise 3.2: myFilterNot
   * The inverse of filter: keeps elements where p(element) == false.
   * Hint: reuse myFilter!
   */
  def myFilterNot(p: A => Boolean): MyList[A] =
    ???
  
  
  // ============================================
  // Part 4: flatMap - The key to Monads!
  // ============================================
  
  /**
   * Exercise 4.1: myFlatMap
   * 
   * flatMap is THE central function of monads.
   * It does two things:
   * 1. Applies f to each element (like map)
   * 2. "Flattens" the result (concatenates all lists into one)
   * 
   * Difference with map:
   * - map(f: A => B) : List[A] => List[B]
   * - flatMap(f: A => List[B]) : List[A] => List[B]
   * 
   * Example:
   *   MyCons(1, MyCons(2, MyNil)).myFlatMap(x => MyCons(x, MyCons(x * 10, MyNil)))
   *   == MyCons(1, MyCons(10, MyCons(2, MyCons(20, MyNil))))
   * 
   * Steps for [1, 2].flatMap(x => [x, x*10]):
   *   1 -> [1, 10]
   *   2 -> [2, 20]
   *   Result: [1, 10] ++ [2, 20] = [1, 10, 2, 20]
   */
  def myFlatMap[B](f: A => MyList[B]): MyList[B] =
    ???
  
  /**
   * Exercise 4.2: myMap in terms of myFlatMap
   * You can implement map using flatMap!
   * This shows that flatMap is more "powerful" than map.
   * 
   * Hint: f: A => B, you need to return a list with a single element
   */
  def myMapViaFlatMap[B](f: A => B): MyList[B] =
    ???
  
  
  // ============================================
  // Part 5: fold - The most powerful operation
  // ============================================
  
  /**
   * Exercise 5.1: myFoldLeft
   * 
   * fold "reduces" a list to a single value by applying
   * a combining function element by element.
   * 
   * foldLeft traverses from left to right:
   *   [1, 2, 3].foldLeft(0)(_ + _)
   *   = ((0 + 1) + 2) + 3
   *   = 6
   * 
   * @param z the initial value (or "zero")
   * @param f the combining function (accumulator, element) => new accumulator
   * 
   * Example: MyCons(1, MyCons(2, MyCons(3, MyNil))).myFoldLeft(0)((acc, x) => acc + x) == 6
   */
  def myFoldLeft[B](z: B)(f: (B, A) => B): B =
    ???
  
  /**
   * Exercise 5.2: myFoldRight
   * 
   * foldRight traverses from right to left:
   *   [1, 2, 3].foldRight(0)(_ + _)
   *   = 1 + (2 + (3 + 0))
   *   = 6
   * 
   * Note: The order of parameters of f is reversed!
   * 
   * @param z the initial value
   * @param f the function (element, accumulator) => new accumulator
   */
  def myFoldRight[B](z: B)(f: (A, B) => B): B =
    ???
  
  // The sum and product methods are defined as extensions below
  
  /**
   * Exercise 5.5: myForall
   * Returns true if ALL elements satisfy the predicate.
   * An empty list returns true.
   * 
   * Example: MyCons(2, MyCons(4, MyNil)).myForall(x => x % 2 == 0) == true
   */
  def myForall(p: A => Boolean): Boolean =
    ???
  
  /**
   * Exercise 5.6: myExists
   * Returns true if AT LEAST ONE element satisfies the predicate.
   * An empty list returns false.
   */
  def myExists(p: A => Boolean): Boolean =
    ???
  
  
  // ============================================
  // Part 6: Other useful operations
  // ============================================
  
  /**
   * Exercise 6.1: take
   * Takes the first n elements.
   * 
   * Example: MyCons(1, MyCons(2, MyCons(3, MyNil))).take(2) 
   *          == MyCons(1, MyCons(2, MyNil))
   */
  def take(n: Int): MyList[A] =
    ???
  
  /**
   * Exercise 6.2: drop
   * Removes the first n elements.
   * 
   * Example: MyCons(1, MyCons(2, MyCons(3, MyNil))).drop(2) 
   *          == MyCons(3, MyNil)
   */
  def drop(n: Int): MyList[A] =
    ???
  
  /**
   * Exercise 6.3: zipWith
   * Combines two lists element by element with a function.
   * Stops when one of the lists is empty.
   * 
   * Example: MyCons(1, MyCons(2, MyNil)).zipWith(MyCons(10, MyCons(20, MyNil)))(_ + _)
   *          == MyCons(11, MyCons(22, MyNil))
   */
  def zipWith[B, C](other: MyList[B])(f: (A, B) => C): MyList[C] =
    ???
  
  /**
   * Utility method for display
   */
  override def toString: String = 
    def loop(list: MyList[A], acc: String): String = list match
      case MyNil => acc + "Nil"
      case MyCons(h, t) => loop(t, acc + s"$h :: ")
    loop(this, "")

end MyList


/**
 * Companion object with utility functions
 */
object MyList:
  import MyList.*
  
  /**
   * Creates a MyList from variadic elements.
   * Already implemented for you!
   * 
   * Example: MyList(1, 2, 3) == MyCons(1, MyCons(2, MyCons(3, MyNil)))
   */
  def apply[A](elements: A*): MyList[A] =
    if elements.isEmpty then MyNil
    else MyCons(elements.head, apply(elements.tail*))
  
  /**
   * Exercise 2.2: doubleAll
   * Use myMap to double all elements.
   */
  def doubleAll(list: MyList[Int]): MyList[Int] =
    ???
  
  /**
   * Exercise 2.3: convertToStrings
   * Use myMap to convert all integers to String.
   */
  def convertToStrings(list: MyList[Int]): MyList[String] =
    ???
  
  /**
   * Exercise 3.3: keepPositives
   * Use myFilter to keep only positive numbers.
   */
  def keepPositives(list: MyList[Int]): MyList[Int] =
    ???
  
  /**
   * Exercise 4.3: duplicateEach
   * Use myFlatMap to duplicate each element.
   * [1, 2, 3] -> [1, 1, 2, 2, 3, 3]
   */
  def duplicateEach[A](list: MyList[A]): MyList[A] =
    ???
  
  /**
   * Exercise 4.4: expandRange
   * Use myFlatMap to transform [1, 3] into [0, 1, 0, 1, 2, 3]
   * Each n becomes the list [0, 1, ..., n]
   */
  def expandRange(list: MyList[Int]): MyList[Int] =
    ???
  
  // Helper function to create a range
  def range(start: Int, end: Int): MyList[Int] =
    if start > end then MyNil
    else MyCons(start, range(start + 1, end))
  
  /**
   * Exercise 5.3: sum
   * Calculate the sum of elements in an integer list.
   * Use myFoldLeft.
   */
  def sum(list: MyList[Int]): Int =
    ???
  
  /**
   * Exercise 5.4: product
   * Calculate the product of elements in an integer list.
   */
  def product(list: MyList[Int]): Int =
    ???


// ============================================
// BONUS: Advanced Exercises
// ============================================

object Bonus:
  import MyList.*
  
  /**
   * BONUS 1: Implement filter via foldRight
   */
  def filterViaFold[A](list: MyList[A])(p: A => Boolean): MyList[A] =
    ???
  
  /**
   * BONUS 2: Implement map via foldRight
   */
  def mapViaFold[A, B](list: MyList[A])(f: A => B): MyList[B] =
    ???
  
  /**
   * BONUS 3: Implement flatMap via foldRight
   */
  def flatMapViaFold[A, B](list: MyList[A])(f: A => MyList[B]): MyList[B] =
    ???
