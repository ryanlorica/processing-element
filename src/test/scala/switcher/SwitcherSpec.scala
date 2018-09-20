package switcher

import org.scalatest.{FlatSpec, Matchers}

import chisel3._

import procelem._
import procelem.enums._
import procelem.components.Switcher

class SwitcherSpec extends FlatSpec with Matchers {

  behavior of "Switcher"

  it should "Instantiate correctly" in {

    val exMem: Map[RFType, Int] = Map(WRF -> 256, ARF -> 256, PRF -> 256)
    val context = Config(PE.allComponents, exMem, (4, 4), INT8)

    chisel3.iotesters.Driver(() => new Switcher(context)) { c =>
      new InstantiateTest(c)
    } should be(true)
  }
}
