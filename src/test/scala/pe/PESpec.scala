package pe

import org.scalatest.{FlatSpec, Matchers}

import procelem._
import procelem.enums._

class PESpec extends FlatSpec with Matchers {

  behavior of "PE"

  it should "Instantiate correctly" in {

    val exMem: Map[RFType, Int] = Map(WRF -> 256, ARF -> 256, PRF -> 256)
    val config = Config(Nil, exMem, (4, 4), INT8)

    chisel3.iotesters.Driver(() => new PE(config)) { c =>
      new InstantiateTest(c)
    } should be(true)
  }
}
