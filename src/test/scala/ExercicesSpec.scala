package seance3

import munit.FunSuite
import MyList.*

class ExercicesSpec extends FunSuite:
  
  // Listes de test
  val empty: MyList[Int] = MyNil
  val one: MyList[Int] = MyCons(1, MyNil)
  val three: MyList[Int] = MyCons(1, MyCons(2, MyCons(3, MyNil)))
  val five: MyList[Int] = MyList(1, 2, 3, 4, 5)
  
  // ============================================
  // Tests Partie 1: Opérations de Base
  // ============================================
  
  test("1.1 - isEmpty"):
    assert(empty.isEmpty)
    assert(!one.isEmpty)
    assert(!three.isEmpty)
  
  test("1.2 - length"):
    assertEquals(empty.length, 0)
    assertEquals(one.length, 1)
    assertEquals(three.length, 3)
    assertEquals(five.length, 5)
  
  test("1.3 - headOption"):
    assertEquals(empty.headOption, None)
    assertEquals(one.headOption, Some(1))
    assertEquals(three.headOption, Some(1))
  
  test("1.4 - lastOption"):
    assertEquals(empty.lastOption, None)
    assertEquals(one.lastOption, Some(1))
    assertEquals(three.lastOption, Some(3))
    assertEquals(five.lastOption, Some(5))
  
  test("1.5 - append (++)"):
    assertEquals(empty ++ empty, empty)
    assertEquals(empty ++ one, one)
    assertEquals(one ++ empty, one)
    assertEquals(MyCons(1, MyCons(2, MyNil)) ++ MyCons(3, MyNil), three)
  
  test("1.6 - reverse"):
    assertEquals(empty.reverse, empty)
    assertEquals(one.reverse, one)
    assertEquals(three.reverse, MyList(3, 2, 1))
    assertEquals(five.reverse, MyList(5, 4, 3, 2, 1))
  
  
  // ============================================
  // Tests Partie 2: map
  // ============================================
  
  test("2.1 - myMap: transforme les éléments"):
    assertEquals(empty.myMap(_ * 2), empty)
    assertEquals(one.myMap(_ * 2), MyCons(2, MyNil))
    assertEquals(three.myMap(_ * 2), MyList(2, 4, 6))
    assertEquals(three.myMap(_.toString), MyList("1", "2", "3"))
  
  test("2.2 - doublerTous"):
    assertEquals(MyList.doublerTous(empty), empty)
    assertEquals(MyList.doublerTous(three), MyList(2, 4, 6))
  
  test("2.3 - convertirEnStrings"):
    assertEquals(MyList.convertirEnStrings(three), MyList("1", "2", "3"))
  
  
  // ============================================
  // Tests Partie 3: filter
  // ============================================
  
  test("3.1 - myFilter: garde les éléments correspondants"):
    assertEquals(empty.myFilter(_ > 0), empty)
    assertEquals(three.myFilter(_ > 1), MyList(2, 3))
    assertEquals(five.myFilter(_ % 2 == 0), MyList(2, 4))
    assertEquals(three.myFilter(_ > 10), empty)
  
  test("3.2 - myFilterNot"):
    assertEquals(three.myFilterNot(_ > 1), MyList(1))
    assertEquals(five.myFilterNot(_ % 2 == 0), MyList(1, 3, 5))
  
  test("3.3 - garderPositifs"):
    assertEquals(MyList.garderPositifs(MyList(-1, 2, -3, 4)), MyList(2, 4))
    assertEquals(MyList.garderPositifs(MyList(-1, -2)), empty)
  
  
  // ============================================
  // Tests Partie 4: flatMap
  // ============================================
  
  test("4.1 - myFlatMap: applique et aplatit"):
    assertEquals(empty.myFlatMap(x => MyList(x, x)), empty)
    assertEquals(one.myFlatMap(x => MyList(x, x * 10)), MyList(1, 10))
    assertEquals(
      MyCons(1, MyCons(2, MyNil)).myFlatMap(x => MyList(x, x * 10)),
      MyList(1, 10, 2, 20)
    )
  
  test("4.2 - myMapViaFlatMap"):
    assertEquals(three.myMapViaFlatMap(_ * 2), MyList(2, 4, 6))
    assertEquals(three.myMapViaFlatMap(_.toString), MyList("1", "2", "3"))
  
  test("4.3 - dupliquerChaque"):
    assertEquals(MyList.dupliquerChaque(three), MyList(1, 1, 2, 2, 3, 3))
    assertEquals(MyList.dupliquerChaque(MyList("a", "b")), MyList("a", "a", "b", "b"))
  
  test("4.4 - expandRange"):
    assertEquals(MyList.expandRange(MyList(2)), MyList(0, 1, 2))
    assertEquals(MyList.expandRange(MyList(1, 2)), MyList(0, 1, 0, 1, 2))
  
  
  // ============================================
  // Tests Partie 5: fold
  // ============================================
  
  test("5.1 - myFoldLeft"):
    assertEquals(empty.myFoldLeft(0)(_ + _), 0)
    assertEquals(three.myFoldLeft(0)(_ + _), 6)
    assertEquals(three.myFoldLeft(10)(_ + _), 16)
    assertEquals(three.myFoldLeft("")((acc, x) => acc + x.toString), "123")
  
  test("5.2 - myFoldRight"):
    assertEquals(empty.myFoldRight(0)(_ + _), 0)
    assertEquals(three.myFoldRight(0)(_ + _), 6)
    assertEquals(three.myFoldRight("")((x, acc) => x.toString + acc), "123")
  
  test("5.3 - sum"):
    assertEquals(MyList.sum(empty), 0)
    assertEquals(MyList.sum(three), 6)
    assertEquals(MyList.sum(five), 15)
  
  test("5.4 - product"):
    assertEquals(MyList.product(one), 1)
    assertEquals(MyList.product(three), 6)
    assertEquals(MyList.product(MyList(2, 3, 4)), 24)
  
  test("5.5 - myForall"):
    assert(empty.myForall(_ > 0))
    assert(three.myForall(_ > 0))
    assert(!three.myForall(_ > 1))
    assert(five.myForall(_ <= 5))
  
  test("5.6 - myExists"):
    assert(!empty.myExists(_ > 0))
    assert(three.myExists(_ > 2))
    assert(!three.myExists(_ > 10))
    assert(five.myExists(_ == 3))
  
  
  // ============================================
  // Tests Partie 6: Autres opérations
  // ============================================
  
  test("6.1 - take"):
    assertEquals(empty.take(2), empty)
    assertEquals(five.take(0), empty)
    assertEquals(five.take(3), MyList(1, 2, 3))
    assertEquals(five.take(10), five)
  
  test("6.2 - drop"):
    assertEquals(empty.drop(2), empty)
    assertEquals(five.drop(0), five)
    assertEquals(five.drop(3), MyList(4, 5))
    assertEquals(five.drop(10), empty)
  
  test("6.3 - zipWith"):
    assertEquals(
      MyList(1, 2, 3).zipWith(MyList(10, 20, 30))(_ + _),
      MyList(11, 22, 33)
    )
    assertEquals(
      MyList(1, 2).zipWith(MyList(10, 20, 30))(_ + _),
      MyList(11, 22)
    )
    assertEquals(
      empty.zipWith(three)(_ + _),
      empty
    )
  
  
  // ============================================
  // Tests BONUS
  // ============================================
  
  test("BONUS 1 - filterViaFold"):
    assertEquals(Bonus.filterViaFold(three)(_ > 1), MyList(2, 3))
    assertEquals(Bonus.filterViaFold(five)(_ % 2 == 0), MyList(2, 4))
  
  test("BONUS 2 - mapViaFold"):
    assertEquals(Bonus.mapViaFold(three)(_ * 2), MyList(2, 4, 6))
  
  test("BONUS 3 - flatMapViaFold"):
    assertEquals(
      Bonus.flatMapViaFold(MyCons(1, MyCons(2, MyNil)))(x => MyList(x, x * 10)),
      MyList(1, 10, 2, 20)
    )
