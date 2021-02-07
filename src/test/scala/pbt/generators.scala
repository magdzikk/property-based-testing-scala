package pbt

import org.scalacheck.Gen

import java.time.LocalDate


object generators {

  private val OLDEST_DAY_OF_BIRTH = LocalDate.of(1900, 1, 1)
  private val START_OF_BUSINESS = LocalDate.of(2010, 1, 1)

  def dateFromRangeGen(rangeStart: LocalDate, rangeEnd: LocalDate): Gen[LocalDate] = {
    Gen.choose(rangeStart.toEpochDay, rangeEnd.toEpochDay).map(i => LocalDate.ofEpochDay(i))
  }

  val customerGen: Gen[Customer] = for {
    name <- Gen.alphaStr
    dateJoined <- dateFromRangeGen(START_OF_BUSINESS, LocalDate.now)
    dateOfBirth <- dateFromRangeGen(OLDEST_DAY_OF_BIRTH, LocalDate.now.minusYears(18))
  } yield Customer(name, dateJoined, dateOfBirth)

  val futureDateGen: Gen[LocalDate] = dateFromRangeGen(LocalDate.now, LocalDate.of(2200, 12, 31))
}
