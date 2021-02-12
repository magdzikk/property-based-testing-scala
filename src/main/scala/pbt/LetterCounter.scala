package pbt

import scala.collection.mutable

class LetterCounter {

  def countOccurrences(sentence: String): Map[Char, Int] = {
    val map = new mutable.HashMap[Char, Int]
    sentence.tapEach { c =>
      map.put(c, map.getOrElse(c, 0) + 1)
    }
    Map.from(map)
  }
}
