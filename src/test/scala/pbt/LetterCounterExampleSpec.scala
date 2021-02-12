package pbt

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LetterCounterExampleSpec extends AnyFlatSpec with Matchers {

  private val counter = new LetterCounter

  "Letter counter" should "count one ocurrence" in {
    val map = counter.countOccurrences("Banana bread")
    map.get('B') should be(Some(1))
  }

  it should "count multiple ocurrences" in {
    val map = counter.countOccurrences("Banana bread")
    map.get('a') should be(Some(4))
  }

  it should "have no entry for no occurence" in {
    val map = counter.countOccurrences("Banana bread")
    map.get('Q') should be(None)
  }

  it should "count occurences for each letter" in {
    val map = counter.countOccurrences("Banana bread")
    val expected = Map('B' -> 1, 'a' -> 4, 'n'-> 2, ' ' -> 1, 'b' -> 1, 'r' -> 1, 'e' -> 1, 'd' -> 1)
    map should contain theSameElementsAs expected
  }
}
