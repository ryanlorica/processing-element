package rf

import chisel3.iotesters.PeekPokeTester
import pe.rf.RF

import scala.math._
import scala.util._

class RFReadWriteTest(c: RF) extends PeekPokeTester(c) {

  private val maxNum = pow(2, c.dataWidth).toInt - 1
  val random = new Random(42)

  private val golden = scala.collection.mutable.ArrayBuffer.empty[Int]

  poke(c.io.control.wEnable, 1)
  poke(c.io.control.rEnable, 1)

  for (x <- 0 until c.simdWidth) {

    for (y <- 0 until c.memSize) {
      val num = random.nextInt(maxNum)
      golden += num

      poke(c.io.control.wAddr(x), y)
      poke(c.io.in(x), num)
      step(1)
    }

    for (y <- 0 until c.memSize) {
      poke(c.io.control.rAddr(x), y)
      expect(c.io.out(x), golden(y))
    }
  }
}
