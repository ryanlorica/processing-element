package switcher

import org.scalatest.{FlatSpec, Matchers}

import pe._
import pe.switcher._

class SwitcherSpec extends FlatSpec with Matchers {

  behavior of "Switcher"

  it should "Instantiate Correctly" in {

    val modExists: Map[TModule, Boolean] =
      Map(WRF -> true, ARF -> true, MBL -> true, ABL -> true, PRF -> true, NBL -> true)
    val memSize: Map[TModule, Int] =
      Map(WRF -> 128, ARF -> 128, PRF -> 128)
    val encoding = INT8
    val simdM = 4
    val simdN = 4
    val numThickIn = 3
    val numThinIn = 3
    val numThickOut = 3
    val numThinOut = 3
    val adderTreeEn = true
    val adderParaEn = true

    val config = PEConfiguration(
      modExists,
      memSize,
      encoding,
      simdM,
      simdN,
      numThickIn,
      numThinIn,
      numThickOut,
      numThinOut,
      adderTreeEn,
      adderParaEn
    )

    chisel3.iotesters.Driver(() => new Switcher(config)) { c =>
      new InstantiateSwitcher(c)
    } should be(true)
  }
}
