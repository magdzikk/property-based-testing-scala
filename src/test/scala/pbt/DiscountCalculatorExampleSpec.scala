package pbt

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.LocalDate

class DiscountCalculatorExampleSpec extends AnyFlatSpec with Matchers {
  private val calculator = new DiscountCalculator
  private val NOW = LocalDate.of(2019, 9, 1)

  "Discount calculator" should "return 10% discount for a one year member" in {
    val dateOfBirth = LocalDate.of(1980, 1, 20)
    val customer = Customer("Sue Smith", NOW.minusYears(1), dateOfBirth)

    val discount = calculator.calculateDiscount(customer, NOW)

    discount should be(BigDecimal(0.1))
  }

  it should "return 20% discount for a two year member" in {
    val dateOfBirth = LocalDate.of(1980, 1, 20)
    val customer = Customer("Sue Smith", NOW.minusYears(2), dateOfBirth)

    val discount = calculator.calculateDiscount(customer, NOW)

    discount should be(BigDecimal(0.2))
  }

  it should "return 20% discount for a three year member" in {
    val dateOfBirth = LocalDate.of(1980, 1, 20)
    val customer = Customer("Sue Smith", NOW.minusYears(3), dateOfBirth)

    val discount = calculator.calculateDiscount(customer, NOW)

    discount should be(BigDecimal(0.2))
  }

  it should "return no discount for a new member" in {
    val customer = Customer("Sue Smith", NOW, LocalDate.of(1980, 1, 20))

    val discount = calculator.calculateDiscount(customer, NOW)

    discount should be(BigDecimal(0))
  }

  it should "return birthday discount" in {
    val customer = Customer("Sue Smith", NOW.minusYears(1), NOW.withYear(1980))

    val discount = calculator.calculateDiscount(customer, NOW)

    discount should be(BigDecimal(0.3))
  }

}
