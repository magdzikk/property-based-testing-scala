package pbt

import org.scalacheck.Gen

import java.time.LocalDate


object generators {

  private val OLDEST_DAY_OF_BIRTH = LocalDate.of(1950, 1, 1)
  private val START_OF_BUSINESS = LocalDate.of(2005, 1, 1)

  val customerGen: Gen[Customer] = for {
    name <- Gen.alphaStr
    dateJoined <- dateFromRangeGen(START_OF_BUSINESS, LocalDate.now)
    dateOfBirth <- dateFromRangeGen(OLDEST_DAY_OF_BIRTH, LocalDate.now.minusYears(18))
  } yield Customer(name, dateJoined, dateOfBirth)

  val futureDateGen: Gen[LocalDate] = dateFromRangeGen(LocalDate.now, LocalDate.of(2050, 12, 31))

  private def dateFromRangeGen(rangeStart: LocalDate, rangeEnd: LocalDate): Gen[LocalDate] = {
    Gen.choose(rangeStart.toEpochDay, rangeEnd.toEpochDay).map(i => LocalDate.ofEpochDay(i))
  }
}
