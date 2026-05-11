# Session 3 - Monads and Higher-Order Functions (Part 1)

**Duration**: 4h  
**Date**: May 5, 2025

## Main Objective

**Implement our own List!**

By recreating the standard library, you will understand:
- How `map`, `flatMap`, `filter`, `fold` work "under the hood"
- Why these functions are so powerful
- What a Monad is (without mathematical jargon)

## Recap: Previous Sessions

- **Session 1**: Scala basics, recursion, simple pattern matching
- **Session 2**: Case classes, sealed traits, enums, advanced pattern matching

## Getting Started

```bash
cd seance-3
sbt compile
sbt test
```

## Structure of MyList

```scala
enum MyList[+A]:
  case MyNil                           // Empty list
  case MyCons(head: A, tail: MyList[A]) // Element + rest
```

**Visual Example:**
```
MyList(1, 2, 3) = MyCons(1, MyCons(2, MyCons(3, MyNil)))

┌───┬───┐   ┌───┬───┐   ┌───┬───┐   ┌─────┐
│ 1 │ ●─┼──►│ 2 │ ●─┼──►│ 3 │ ●─┼──►│ Nil │
└───┴───┘   └───┴───┘   └───┴───┘   └─────┘
```

## Session Organization

### Part 1: Basic Operations (45 min)

**Concepts:** Recursive structure, pattern matching

The pattern is always the same:

```scala
def operation: Result = this match
  case MyNil => ???        // Base case: what to return for an empty list?
  case MyCons(h, t) => ??? // Recursive case: use h (head) and t.operation
```

For example, to check if all elements are positive:

```scala
def allPositive: Boolean = this match
  case MyNil => true                      // Empty list = all positive (by default)
  case MyCons(h, t) => h > 0 && t.allPositive  // h positive AND the rest too
```

**Exercises 1.1 - 1.6**: `isEmpty`, `length`, `headOption`, `lastOption`, `++`, `reverse`

### Part 2: map - Transform (45 min)

**The key concept:**

`map` applies a function to **each element** without changing the structure.

```
[1, 2, 3].map(x => x * 2) = [2, 4, 6]
     │          │               │
     └──────────┼───────────────┘
         The structure remains identical!
```

**Signature:**
```scala
def myMap[B](f: A => B): MyList[B]
```

**Remember:**
- If the list has 5 elements, `myMap` returns 5 elements
- The type can change (`MyList[Int]` → `MyList[String]`)
- Order is preserved

**Exercises 2.1 - 2.3**

### Part 3: filter - Select (30 min)

`filter` keeps only elements that satisfy a predicate.

```
[1, 2, 3, 4].filter(x => x % 2 == 0) = [2, 4]
```

**Exercises 3.1 - 3.3**

### Part 4: flatMap - The Key to Monads! (60 min)

**This is THE central concept!**

`flatMap` does two things:
1. Applies a function that returns a list
2. "Flattens" the result

```
[1, 2].flatMap(x => [x, x*10])

Step 1 - Apply:
  1 → [1, 10]
  2 → [2, 20]
  
Intermediate result: [[1, 10], [2, 20]]

Step 2 - Flatten:
  [1, 10, 2, 20]
```

**Difference map vs flatMap:**

```scala
// map: A => B
[1, 2].map(x => x * 2)        // [2, 4]

// flatMap: A => List[B]
[1, 2].flatMap(x => [x, x])   // [1, 1, 2, 2]
```

**Why is it powerful?**

`flatMap` allows you to "chain" operations that can produce multiple results:

```scala
// All pairs (x, y) where x comes from [1,2] and y comes from [3,4]
MyList(1, 2).myFlatMap(x =>
  MyList(3, 4).myMap(y =>
    (x, y)
  )
)
// Result: [(1,3), (1,4), (2,3), (2,4)]
```

**Exercises 4.1 - 4.4**

### Part 5: fold - The Ultimate Operation (60 min)

`fold` "reduces" a list to a single value.

**foldLeft** traverses from left to right:
```
[1, 2, 3].foldLeft(0)(_ + _)
= ((0 + 1) + 2) + 3
= 6
```

**foldRight** traverses from right to left:
```
[1, 2, 3].foldRight(0)(_ + _)
= 1 + (2 + (3 + 0))
= 6
```

**fold is VERY powerful** - you can implement map, filter, flatMap with fold!

**Exercises 5.1 - 5.6**

### Part 6: Other Operations (30 min)

**Exercises 6.1 - 6.3**: `take`, `drop`, `zipWith`

## Key Concepts to Remember

### The Signature Reveals Everything

| Function | Signature | What it does |
|----------|-----------|--------------|
| `map` | `(A => B) => List[B]` | Transforms each element |
| `filter` | `(A => Boolean) => List[A]` | Keeps if true |
| `flatMap` | `(A => List[B]) => List[B]` | Transforms + flattens |
| `foldLeft` | `(B)((B, A) => B) => B` | Reduces to a value |

### Map Never Changes the Structure!

```scala
List(1, 2, 3).map(???)      // Always 3 elements
Some(5).map(???)            // Always Some
None.map(???)               // Always None
Right(x).map(???)           // Always Right
```

That's why we say `map` modifies the **content** but not the **container**.

### FlatMap Allows You to "Exit" the Container

```scala
Some(5).flatMap(x => if x > 0 then Some(x) else None)
// Can return Some or None!

List(1, 2).flatMap(x => List(x, x))
// Can change the size!
```

## Why "Monad"?

A **Monad** is simply a type that has:
1. A way to create an instance (`pure` or constructor)
2. A `flatMap` function

`List`, `Option`, `Either`, `Try` are all monads!

In the next session, we will implement `MyOption`, `MyEither`, `MyTry` to solidify these concepts.

## Useful Commands

```bash
sbt test                    # All tests
sbt "testOnly *1.1*"        # Specific test
sbt ~test                   # Watch mode
sbt console                 # REPL
```

In the REPL:
```scala
import seance3.*
import MyList.*

val l = MyList(1, 2, 3)
l.myMap(_ * 2)
l.myFilter(_ > 1)
l.myFlatMap(x => MyList(x, x))
```

## Common Errors

**"match may not be exhaustive"**  
→ Make sure to cover `MyNil` AND `MyCons`

**Stack overflow on large lists**  
→ Normal for non tail-recursive recursion. We'll see optimizations later.

**Type mismatch with MyNil**  
→ Sometimes you need to annotate: `MyNil: MyList[Int]`

## Resources

- [Functional Programming in Scala](https://www.manning.com/books/functional-programming-in-scala) - Chapter 3
- [Scala Documentation - Collections](https://docs.scala-lang.org/overviews/collections-2.13/introduction.html)

---

*FP with Scala - V2 - Session 3*
