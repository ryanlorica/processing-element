package rf

import org.scalatest.{FlatSpec, Matchers}

import pe.rf._

class RFSpec extends FlatSpec with Matchers {

  behavior of "RF"

  it should "Read and Write Correctly" in {
    chisel3.iotesters.Driver(() => new RF(64, 8, 1)) { c =>
      new RFReadWriteTest(c)
    } should be(true)
  }
}
