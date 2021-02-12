package pbt

import org.scalatest.matchers.should.Matchers
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class LetterCounterPropertySpec extends AnyPropSpec with ScalaCheckPropertyChecks with Matchers {
  val letterCounter = new LetterCounter

  property("Sum of all occurrences should equal sentence length") {
    forAll { (sentence: String) =>
      val sum = letterCounter.countOccurrences(sentence).values.map(identity).sum

      sum should be(sentence.length)
    }

  }
}
