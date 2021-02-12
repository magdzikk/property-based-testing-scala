package pbt

import org.scalacheck.{Arbitrary, Properties}
import org.scalacheck.Prop.{forAll, propBoolean}
import pbt.generators.{customerGen, futureDateGen}

import java.time.LocalDate

object DiscountCalculatorPropertyScalacheckSpec extends Properties("Discount calculator") {
  private val calculator = new DiscountCalculator

  implicit val arb: Arbitrary[LocalDate] = Arbitrary(futureDateGen)
  implicit val arb2: Arbitrary[Customer] = Arbitrary(customerGen)

  property("should not be over 30 percent") = forAll { (customer: Customer, now: LocalDate) =>
    val discount = calculator.calculateDiscount(customer, now)
    discount.compare(BigDecimal(0.3)) <= 0
  }

  property("Discount should be proportional to membership years") =
    forAll { (olderCustomer: Customer, newerCustomer: Customer, now: LocalDate) =>
      (olderCustomer.joinedAt.isBefore(newerCustomer.joinedAt) && isNotBirthday(olderCustomer, now) && isNotBirthday(newerCustomer, now)) ==> {

        val discountForOlderCustomer = calculator.calculateDiscount(olderCustomer, now)
        val discountForNewerCustomer = calculator.calculateDiscount(newerCustomer, now)
        discountForOlderCustomer.compare(discountForNewerCustomer) >= 0
      }
    }

  property("Birthday discount should be highest") =
    forAll { (customer: Customer, birthdayCustomer: Customer) =>
      val now = birthdayCustomer.dateOfBirth.withYear(2021)
      isNotBirthday(customer, now) ==> {
        val ordinaryDiscount = calculator.calculateDiscount(customer, now)
        val birthdayDiscount = calculator.calculateDiscount(birthdayCustomer, now)
        birthdayDiscount.compare(ordinaryDiscount) >= 0
      }
    }


  private def isNotBirthday(customer: Customer, now: LocalDate) =
    customer.dateOfBirth.getMonthValue != now.getMonthValue || customer.dateOfBirth.getDayOfMonth != now.getDayOfMonth
}
