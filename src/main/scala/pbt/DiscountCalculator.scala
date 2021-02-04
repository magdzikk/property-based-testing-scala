package pbt

import java.time.LocalDate
import java.time.temporal.ChronoUnit.YEARS

class DiscountCalculator {
  def calculateDiscount(customer: Customer, now: LocalDate): BigDecimal = {
    val birthdayThisYear = LocalDate.of(now.getYear, customer.dateOfBirth.getMonthValue, customer.dateOfBirth.getDayOfMonth)

    if (now.isEqual(birthdayThisYear)) return BigDecimal("0.3")

    val yearsOfMembership = YEARS.between(customer.joinedAt, now)
    yearsOfMembership.toInt match {
      case 0 =>
        BigDecimal("0")
      case 1 =>
        BigDecimal("0.1")
      case _ =>
        BigDecimal("0.2")
    }
  }

}
