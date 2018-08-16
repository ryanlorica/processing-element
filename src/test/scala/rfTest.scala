import chisel3._
import chisel3.iotesters.PeekPokeTester

import org.scalatest._

import pe._

import scala.math.pow

class RFInstanceTest(c: RF) extends PeekPokeTester(c) {
  private val maxNum = pow(2, c.dataWidth).toInt - 1

  poke(c.io.control.wEnable, 1)
  poke(c.io.control.rEnable, 1)

  for (x <- c.simdWidth) {
    for (y <- c.memsize) {
      poke(c.io.control.wAddr(x), y)
      poke(c.io.in(x), (y + x) % c.memsize)
    }
    for (y <- c.memsize) {
      poke(c.io.control.rAddr(x), y)
      expect(c.io.out(x), (y + x) % c.memsize)
    }
  }
}

class RFSpec extends FlatSpec with Matchers {

  behavior of "RF"

  it should "Instantiate Correctly" in {
    chisel3.iotesters
  }
}
