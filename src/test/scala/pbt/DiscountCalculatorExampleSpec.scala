package pbt

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.time.LocalDate

class DiscountCalculatorExampleSpec extends AnyWordSpec with Matchers{
  private val calculator = new DiscountCalculator
  private val NOW = LocalDate.of(2019, 9, 1)

  "Discount calculator" should {
    "return 10 percent discount" in {
      val dateOfBirth = LocalDate.of(1980, 1, 20)
      val customer = Customer("Sue Smith", NOW.minusYears(1), dateOfBirth)

      val discount = calculator.calculateDiscount(customer, NOW)

      discount should be(BigDecimal(0.1))
    }

    "return 20 percent discount" when {
      "the customer is a two year member" in {
        val dateOfBirth = LocalDate.of(1980, 1, 20)
        val customer = Customer("Sue Smith", NOW.minusYears(2), dateOfBirth)

        val discount = calculator.calculateDiscount(customer, NOW)

        discount should be(BigDecimal(0.2))
      }

      "the customer is a three year member" in {
        val dateOfBirth = LocalDate.of(1980, 1, 20)
        val customer = Customer("Sue Smith", NOW.minusYears(3), dateOfBirth)

        val discount = calculator.calculateDiscount(customer, NOW)

        discount should be(BigDecimal(0.2))
      }
    }

    "return no discount" in {
      val customer = Customer("Sue Smith", NOW, LocalDate.of(1980, 1, 20))

      val discount = calculator.calculateDiscount(customer, NOW)

      discount should be(BigDecimal(0))
    }

    "return birthday discount for three year member" in {
      val customer = Customer("Sue Smith", NOW.minusYears(1), NOW.withYear(1980))

      val discount = calculator.calculateDiscount(customer, NOW)

      discount should be(BigDecimal(0.3))
    }
  }

}
