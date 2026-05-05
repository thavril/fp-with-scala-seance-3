package seance3

/**
 * Séance 3 - Monades et Fonctions d'Ordre Supérieur (Partie 1)
 * 
 * OBJECTIF PRINCIPAL : Implémenter notre propre List !
 * 
 * En recréant la librairie standard, vous comprendrez :
 * - Comment fonctionnent map, flatMap, filter, fold "sous le capot"
 * - Pourquoi ces fonctions sont si puissantes
 * - Ce qu'est une Monade (sans le jargon mathématique)
 * 
 * Instructions :
 * 1. Complétez les fonctions marquées avec ???
 * 2. N'utilisez PAS de var, uniquement val
 * 3. Utilisez la récursion (pas de boucles)
 * 4. Lancez les tests avec: sbt test
 */

/**
 * Notre propre implémentation de List !
 * 
 * Une liste est soit :
 * - Vide (MyNil)
 * - Un élément suivi d'une autre liste (MyCons)
 * 
 * Exemple: MyCons(1, MyCons(2, MyCons(3, MyNil))) représente [1, 2, 3]
 */
enum MyList[+A]:
  case MyNil
  case MyCons(head: A, tail: MyList[A])
  
  // ============================================
  // Partie 1: Opérations de Base
  // ============================================
  
  /**
   * Exercice 1.1: isEmpty
   * Retourne true si la liste est vide (MyNil).
   * 
   * Exemple: MyNil.isEmpty == true
   *          MyCons(1, MyNil).isEmpty == false
   */
  def isEmpty: Boolean =
    ???
  
  /**
   * Exercice 1.2: length
   * Retourne le nombre d'éléments dans la liste.
   * Utilisez la récursion !
   * 
   * Exemple: MyNil.length == 0
   *          MyCons(1, MyCons(2, MyNil)).length == 2
   */
  def length: Int =
    ???
  
  /**
   * Exercice 1.3: headOption
   * Retourne Some(premier élément) ou None si la liste est vide.
   * 
   * Exemple: MyCons(1, MyCons(2, MyNil)).headOption == Some(1)
   *          MyNil.headOption == None
   */
  def headOption: Option[A] =
    ???
  
  /**
   * Exercice 1.4: lastOption
   * Retourne Some(dernier élément) ou None si la liste est vide.
   * Utilisez la récursion !
   * 
   * Exemple: MyCons(1, MyCons(2, MyCons(3, MyNil))).lastOption == Some(3)
   */
  def lastOption: Option[A] =
    ???
  
  /**
   * Exercice 1.5: append (++)
   * Concatène deux listes.
   * 
   * Exemple: MyCons(1, MyCons(2, MyNil)) ++ MyCons(3, MyNil) 
   *          == MyCons(1, MyCons(2, MyCons(3, MyNil)))
   */
  def ++[B >: A](other: MyList[B]): MyList[B] =
    ???
  
  /**
   * Exercice 1.6: reverse
   * Inverse l'ordre des éléments.
   * Indice: utilisez un accumulateur ou ++
   * 
   * Exemple: MyCons(1, MyCons(2, MyCons(3, MyNil))).reverse
   *          == MyCons(3, MyCons(2, MyCons(1, MyNil)))
   */
  def reverse: MyList[A] =
    ???
  
  
  // ============================================
  // Partie 2: map - Transformer chaque élément
  // ============================================
  
  /**
   * Exercice 2.1: myMap
   * Applique une fonction f à CHAQUE élément de la liste.
   * 
   * IMPORTANT: myMap ne change JAMAIS la structure de la liste !
   * - Si la liste a 3 éléments, le résultat a 3 éléments
   * - MyNil reste MyNil
   * 
   * Exemple: MyCons(1, MyCons(2, MyCons(3, MyNil))).myMap(x => x * 2)
   *          == MyCons(2, MyCons(4, MyCons(6, MyNil)))
   * 
   * @param f la fonction de transformation A => B
   * @return une nouvelle liste avec les éléments transformés
   */
  def myMap[B](f: A => B): MyList[B] =
    ???
  
  /**
   * Exercice 2.2: Utiliser myMap
   * Utilisez myMap pour doubler tous les éléments d'une liste d'entiers.
   * (Cette fonction est dans l'objet companion ci-dessous)
   */
  
  
  // ============================================
  // Partie 3: filter - Garder certains éléments
  // ============================================
  
  /**
   * Exercice 3.1: myFilter
   * Garde uniquement les éléments qui satisfont le prédicat p.
   * 
   * Exemple: MyCons(1, MyCons(2, MyCons(3, MyCons(4, MyNil)))).myFilter(x => x % 2 == 0)
   *          == MyCons(2, MyCons(4, MyNil))
   * 
   * @param p le prédicat (fonction qui retourne Boolean)
   * @return une nouvelle liste avec uniquement les éléments où p(élément) == true
   */
  def myFilter(p: A => Boolean): MyList[A] =
    ???
  
  /**
   * Exercice 3.2: myFilterNot
   * L'inverse de filter : garde les éléments où p(élément) == false.
   * Indice: réutilisez myFilter !
   */
  def myFilterNot(p: A => Boolean): MyList[A] =
    ???
  
  
  // ============================================
  // Partie 4: flatMap - La clé des Monades !
  // ============================================
  
  /**
   * Exercice 4.1: myFlatMap
   * 
   * flatMap est LA fonction centrale des monades.
   * Elle fait deux choses :
   * 1. Applique f à chaque élément (comme map)
   * 2. "Aplatit" le résultat (concat toutes les listes en une seule)
   * 
   * Différence avec map:
   * - map(f: A => B) : List[A] => List[B]
   * - flatMap(f: A => List[B]) : List[A] => List[B]
   * 
   * Exemple:
   *   MyCons(1, MyCons(2, MyNil)).myFlatMap(x => MyCons(x, MyCons(x * 10, MyNil)))
   *   == MyCons(1, MyCons(10, MyCons(2, MyCons(20, MyNil))))
   * 
   * Étapes pour [1, 2].flatMap(x => [x, x*10]):
   *   1 -> [1, 10]
   *   2 -> [2, 20]
   *   Résultat: [1, 10] ++ [2, 20] = [1, 10, 2, 20]
   */
  def myFlatMap[B](f: A => MyList[B]): MyList[B] =
    ???
  
  /**
   * Exercice 4.2: myMap en termes de myFlatMap
   * On peut implémenter map en utilisant flatMap !
   * Cela montre que flatMap est plus "puissant" que map.
   * 
   * Indice: f: A => B, il faut retourner une liste d'un seul élément
   */
  def myMapViaFlatMap[B](f: A => B): MyList[B] =
    ???
  
  
  // ============================================
  // Partie 5: fold - L'opération la plus puissante
  // ============================================
  
  /**
   * Exercice 5.1: myFoldLeft
   * 
   * fold "réduit" une liste à une seule valeur en appliquant
   * une fonction de combinaison élément par élément.
   * 
   * foldLeft parcourt de gauche à droite:
   *   [1, 2, 3].foldLeft(0)(_ + _)
   *   = ((0 + 1) + 2) + 3
   *   = 6
   * 
   * @param z la valeur initiale (ou "zéro")
   * @param f la fonction de combinaison (accumulateur, élément) => nouvel accumulateur
   * 
   * Exemple: MyCons(1, MyCons(2, MyCons(3, MyNil))).myFoldLeft(0)((acc, x) => acc + x) == 6
   */
  def myFoldLeft[B](z: B)(f: (B, A) => B): B =
    ???
  
  /**
   * Exercice 5.2: myFoldRight
   * 
   * foldRight parcourt de droite à gauche:
   *   [1, 2, 3].foldRight(0)(_ + _)
   *   = 1 + (2 + (3 + 0))
   *   = 6
   * 
   * Note: L'ordre des paramètres de f est inversé !
   * 
   * @param z la valeur initiale
   * @param f la fonction (élément, accumulateur) => nouvel accumulateur
   */
  def myFoldRight[B](z: B)(f: (A, B) => B): B =
    ???
  
  // Les méthodes sum et product sont définies comme extensions ci-dessous
  
  /**
   * Exercice 5.5: myForall
   * Retourne true si TOUS les éléments satisfont le prédicat.
   * Une liste vide retourne true.
   * 
   * Exemple: MyCons(2, MyCons(4, MyNil)).myForall(x => x % 2 == 0) == true
   */
  def myForall(p: A => Boolean): Boolean =
    ???
  
  /**
   * Exercice 5.6: myExists
   * Retourne true si AU MOINS UN élément satisfait le prédicat.
   * Une liste vide retourne false.
   */
  def myExists(p: A => Boolean): Boolean =
    ???
  
  
  // ============================================
  // Partie 6: Autres opérations utiles
  // ============================================
  
  /**
   * Exercice 6.1: take
   * Prend les n premiers éléments.
   * 
   * Exemple: MyCons(1, MyCons(2, MyCons(3, MyNil))).take(2) 
   *          == MyCons(1, MyCons(2, MyNil))
   */
  def take(n: Int): MyList[A] =
    ???
  
  /**
   * Exercice 6.2: drop
   * Supprime les n premiers éléments.
   * 
   * Exemple: MyCons(1, MyCons(2, MyCons(3, MyNil))).drop(2) 
   *          == MyCons(3, MyNil)
   */
  def drop(n: Int): MyList[A] =
    ???
  
  /**
   * Exercice 6.3: zipWith
   * Combine deux listes élément par élément avec une fonction.
   * S'arrête quand une des listes est vide.
   * 
   * Exemple: MyCons(1, MyCons(2, MyNil)).zipWith(MyCons(10, MyCons(20, MyNil)))(_ + _)
   *          == MyCons(11, MyCons(22, MyNil))
   */
  def zipWith[B, C](other: MyList[B])(f: (A, B) => C): MyList[C] =
    ???
  
  /**
   * Méthode utilitaire pour affichage
   */
  override def toString: String = 
    def loop(list: MyList[A], acc: String): String = list match
      case MyNil => acc + "Nil"
      case MyCons(h, t) => loop(t, acc + s"$h :: ")
    loop(this, "")

end MyList


/**
 * Objet companion avec des fonctions utilitaires
 */
object MyList:
  import MyList.*
  
  /**
   * Crée une MyList à partir d'éléments variadiques.
   * Déjà implémenté pour vous !
   * 
   * Exemple: MyList(1, 2, 3) == MyCons(1, MyCons(2, MyCons(3, MyNil)))
   */
  def apply[A](elements: A*): MyList[A] =
    if elements.isEmpty then MyNil
    else MyCons(elements.head, apply(elements.tail*))
  
  /**
   * Exercice 2.2: doublerTous
   * Utilisez myMap pour doubler tous les éléments.
   */
  def doublerTous(liste: MyList[Int]): MyList[Int] =
    ???
  
  /**
   * Exercice 2.3: convertirEnStrings
   * Utilisez myMap pour convertir tous les entiers en String.
   */
  def convertirEnStrings(liste: MyList[Int]): MyList[String] =
    ???
  
  /**
   * Exercice 3.3: garderPositifs
   * Utilisez myFilter pour garder uniquement les nombres positifs.
   */
  def garderPositifs(liste: MyList[Int]): MyList[Int] =
    ???
  
  /**
   * Exercice 4.3: dupliquerChaque
   * Utilisez myFlatMap pour dupliquer chaque élément.
   * [1, 2, 3] -> [1, 1, 2, 2, 3, 3]
   */
  def dupliquerChaque[A](liste: MyList[A]): MyList[A] =
    ???
  
  /**
   * Exercice 4.4: rangeMyList
   * Utilisez myFlatMap pour transformer [1, 3] en [0, 1, 0, 1, 2, 3]
   * Chaque n devient la liste [0, 1, ..., n]
   */
  def expandRange(liste: MyList[Int]): MyList[Int] =
    ???
  
  // Fonction helper pour créer une range
  def range(start: Int, end: Int): MyList[Int] =
    if start > end then MyNil
    else MyCons(start, range(start + 1, end))
  
  /**
   * Exercice 5.3: sum
   * Calculez la somme des éléments d'une liste d'entiers.
   * Utilisez myFoldLeft.
   */
  def sum(liste: MyList[Int]): Int =
    ???
  
  /**
   * Exercice 5.4: product
   * Calculez le produit des éléments d'une liste d'entiers.
   */
  def product(liste: MyList[Int]): Int =
    ???


// ============================================
// BONUS: Exercices Avancés
// ============================================

object Bonus:
  import MyList.*
  
  /**
   * BONUS 1: Implémenter filter via foldRight
   */
  def filterViaFold[A](liste: MyList[A])(p: A => Boolean): MyList[A] =
    ???
  
  /**
   * BONUS 2: Implémenter map via foldRight
   */
  def mapViaFold[A, B](liste: MyList[A])(f: A => B): MyList[B] =
    ???
  
  /**
   * BONUS 3: Implémenter flatMap via foldRight
   */
  def flatMapViaFold[A, B](liste: MyList[A])(f: A => MyList[B]): MyList[B] =
    ???
