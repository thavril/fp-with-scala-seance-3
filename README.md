# Séance 3 - Monades et Fonctions d'Ordre Supérieur (Partie 1)

**Durée** : 4h  
**Date** : 5 mai 2025

## Objectif Principal

**Implémenter notre propre List !**

En recréant la librairie standard, vous comprendrez :
- Comment `map`, `flatMap`, `filter`, `fold` fonctionnent "sous le capot"
- Pourquoi ces fonctions sont si puissantes
- Ce qu'est une Monade (sans jargon mathématique)

## Rappel : Séances précédentes

- **Séance 1** : Bases Scala, récursion, pattern matching simple
- **Séance 2** : Case classes, sealed traits, enums, pattern matching avancé

## Mise en route

```bash
cd seance-3
sbt compile
sbt test
```

## Structure de MyList

```scala
enum MyList[+A]:
  case MyNil                           // Liste vide
  case MyCons(head: A, tail: MyList[A]) // Élément + reste
```

**Exemple visuel :**
```
MyList(1, 2, 3) = MyCons(1, MyCons(2, MyCons(3, MyNil)))

┌───┬───┐   ┌───┬───┐   ┌───┬───┐   ┌─────┐
│ 1 │ ●─┼──►│ 2 │ ●─┼──►│ 3 │ ●─┼──►│ Nil │
└───┴───┘   └───┴───┘   └───┴───┘   └─────┘
```

## Organisation de la séance

### Partie 1 : Opérations de Base (45 min)

**Concepts :** Structure récursive, pattern matching

Le pattern est toujours le même :

```scala
def operation: Result = this match
  case MyNil => ???        // Cas de base : que retourner pour une liste vide ?
  case MyCons(h, t) => ??? // Cas récursif : utiliser h (head) et t.operation
```

Par exemple, pour savoir si tous les éléments sont positifs :

```scala
def tousPositifs: Boolean = this match
  case MyNil => true                      // Liste vide = tous positifs (par défaut)
  case MyCons(h, t) => h > 0 && t.tousPositifs  // h positif ET le reste aussi
```

**Exercices 1.1 - 1.6** : `isEmpty`, `length`, `headOption`, `lastOption`, `++`, `reverse`

### Partie 2 : map - Transformer (45 min)

**Le concept clé :**

`map` applique une fonction à **chaque élément** sans changer la structure.

```
[1, 2, 3].map(x => x * 2) = [2, 4, 6]
     │          │               │
     └──────────┼───────────────┘
         La structure reste identique !
```

**Signature :**
```scala
def myMap[B](f: A => B): MyList[B]
```

**À retenir :**
- Si la liste a 5 éléments, `myMap` retourne 5 éléments
- Le type peut changer (`MyList[Int]` → `MyList[String]`)
- L'ordre est préservé

**Exercices 2.1 - 2.3**

### Partie 3 : filter - Sélectionner (30 min)

`filter` garde uniquement les éléments qui satisfont un prédicat.

```
[1, 2, 3, 4].filter(x => x % 2 == 0) = [2, 4]
```

**Exercices 3.1 - 3.3**

### Partie 4 : flatMap - La Clé des Monades ! (60 min)

**C'est LE concept central !**

`flatMap` fait deux choses :
1. Applique une fonction qui retourne une liste
2. "Aplatit" le résultat

```
[1, 2].flatMap(x => [x, x*10])

Étape 1 - Appliquer:
  1 → [1, 10]
  2 → [2, 20]
  
Résultat intermédiaire: [[1, 10], [2, 20]]

Étape 2 - Aplatir:
  [1, 10, 2, 20]
```

**Différence map vs flatMap :**

```scala
// map: A => B
[1, 2].map(x => x * 2)        // [2, 4]

// flatMap: A => List[B]
[1, 2].flatMap(x => [x, x])   // [1, 1, 2, 2]
```

**Pourquoi c'est puissant ?**

`flatMap` permet de "chaîner" des opérations qui peuvent produire plusieurs résultats :

```scala
// Toutes les paires (x, y) où x vient de [1,2] et y vient de [3,4]
MyList(1, 2).myFlatMap(x =>
  MyList(3, 4).myMap(y =>
    (x, y)
  )
)
// Résultat: [(1,3), (1,4), (2,3), (2,4)]
```

**Exercices 4.1 - 4.4**

### Partie 5 : fold - L'Opération Ultime (60 min)

`fold` "réduit" une liste à une seule valeur.

**foldLeft** parcourt de gauche à droite :
```
[1, 2, 3].foldLeft(0)(_ + _)
= ((0 + 1) + 2) + 3
= 6
```

**foldRight** parcourt de droite à gauche :
```
[1, 2, 3].foldRight(0)(_ + _)
= 1 + (2 + (3 + 0))
= 6
```

**fold est TRÈS puissant** - on peut implémenter map, filter, flatMap avec fold !

**Exercices 5.1 - 5.6**

### Partie 6 : Autres opérations (30 min)

**Exercices 6.1 - 6.3** : `take`, `drop`, `zipWith`

## Concepts Clés à Retenir

### La Signature Révèle Tout

| Fonction | Signature | Ce qu'elle fait |
|----------|-----------|-----------------|
| `map` | `(A => B) => List[B]` | Transforme chaque élément |
| `filter` | `(A => Boolean) => List[A]` | Garde si true |
| `flatMap` | `(A => List[B]) => List[B]` | Transforme + aplatit |
| `foldLeft` | `(B)((B, A) => B) => B` | Réduit à une valeur |

### Map ne Change Jamais la Structure !

```scala
List(1, 2, 3).map(???)      // Toujours 3 éléments
Some(5).map(???)            // Toujours Some
None.map(???)               // Toujours None
Right(x).map(???)           // Toujours Right
```

C'est pourquoi on dit que `map` modifie le **contenu** mais pas le **contenant**.

### FlatMap Permet de "Sortir" du Contenant

```scala
Some(5).flatMap(x => if x > 0 then Some(x) else None)
// Peut retourner Some ou None !

List(1, 2).flatMap(x => List(x, x))
// Peut changer la taille !
```

## Pourquoi "Monade" ?

Une **Monade** est simplement un type qui a :
1. Une façon de créer une instance (`pure` ou constructeur)
2. Une fonction `flatMap`

`List`, `Option`, `Either`, `Try` sont toutes des monades !

La prochaine séance, nous implémenterons `MyOption`, `MyEither`, `MyTry` pour solidifier ces concepts.

## Commandes utiles

```bash
sbt test                    # Tous les tests
sbt "testOnly *1.1*"        # Test spécifique
sbt ~test                   # Mode watch
sbt console                 # REPL
```

Dans le REPL :
```scala
import seance3.*
import MyList.*

val l = MyList(1, 2, 3)
l.myMap(_ * 2)
l.myFilter(_ > 1)
l.myFlatMap(x => MyList(x, x))
```

## Erreurs Courantes

**"match may not be exhaustive"**  
→ Assurez-vous de couvrir `MyNil` ET `MyCons`

**Stack overflow sur grandes listes**  
→ Normal pour récursion non tail-recursive. On verra les optimisations plus tard.

**Type mismatch avec MyNil**  
→ Parfois il faut annoter : `MyNil: MyList[Int]`

## Ressources

- [Functional Programming in Scala](https://www.manning.com/books/functional-programming-in-scala) - Chapitre 3
- [Scala Documentation - Collections](https://docs.scala-lang.org/overviews/collections-2.13/introduction.html)

---

*FP with Scala - V2 - Séance 3*
