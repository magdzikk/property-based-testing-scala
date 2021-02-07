package pbt

import org.scalacheck.Arbitrary
import org.scalatest.matchers.should.Matchers
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import pbt.generators.{customerGen, futureDateGen, nowGen}

import java.time.LocalDate

class DiscountCalculatorPropertySpec extends AnyPropSpec with ScalaCheckPropertyChecks with Matchers {
  private val calculator = new DiscountCalculator

  implicit val arb: Arbitrary[LocalDate] = Arbitrary(futureDateGen)
  implicit val arb2: Arbitrary[Customer] = Arbitrary(customerGen)

  property("Discount should not be over 30 percent") {
    forAll { (customer: Customer, now: LocalDate) =>
      val discount = calculator.calculateDiscount(customer, now)
      discount should be <= BigDecimal(0.3)
    }
  }

  property("Discount should be proportional to membership years") {
    forAll { (olderCustomer: Customer, newerCustomer: Customer, now: LocalDate) =>
      whenever(olderCustomer.joinedAt.isBefore(newerCustomer.joinedAt) && isNotBirthday(olderCustomer, now) && isNotBirthday(newerCustomer, now)) {

        val discountForOlderCustomer = calculator.calculateDiscount(olderCustomer, now)
        val discountForNewerCustomer = calculator.calculateDiscount(newerCustomer, now)
        discountForOlderCustomer.compare(discountForNewerCustomer) should be >= 0
      }
    }
  }

  property("Birthday discount should be highest") {
    forAll { (customer: Customer, birthdayCustomer: Customer) =>
      val now = birthdayCustomer.dateOfBirth.withYear(2021)
      whenever(sameDayOfMonth(birthdayCustomer.dateOfBirth, now) && isNotBirthday(customer, now)) {
        val ordinaryDiscount = calculator.calculateDiscount(customer, now)
        val birthdayDiscount = calculator.calculateDiscount(birthdayCustomer, now)
        birthdayDiscount.compare(ordinaryDiscount) should be >= 0
      }
    }
  }

  private def isNotBirthday(customer: Customer, now: LocalDate) =
    customer.dateOfBirth.getMonthValue != now.getMonthValue || customer.dateOfBirth.getDayOfMonth != now.getDayOfMonth

  private def sameDayOfMonth(date1: LocalDate, date2: LocalDate) = date1.getDayOfMonth == date2.getDayOfMonth
}
