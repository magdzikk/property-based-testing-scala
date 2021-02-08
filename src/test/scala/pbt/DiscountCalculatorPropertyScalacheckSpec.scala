package pbt

import org.scalacheck.{Arbitrary, Properties}
import org.scalacheck.Prop.forAll
import pbt.generators.{customerGen, futureDateGen}

import java.time.LocalDate

object DiscountCalculatorPropertyScalacheckSpec extends Properties("Discount calculator") {
  private val calculator = new DiscountCalculator

  implicit val arb: Arbitrary[LocalDate] = Arbitrary(futureDateGen)
  implicit val arb2: Arbitrary[Customer] = Arbitrary(customerGen)

  property("should not be over 30 percent") = forAll { (customer: Customer, now: LocalDate) =>
    calculator.calculateDiscount(customer, now).compare(BigDecimal(0.3)) <= 0
  }

}
