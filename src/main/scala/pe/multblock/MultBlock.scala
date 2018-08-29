package pe.multblock

import chisel3._
import chisel3.util._

import pe._

class MultBlock(c: PEConfig) extends Module {

  val io = IO(new Bundle {
    val enable = Bool()
    val data = new MultBlockIO(c)
  })

  // TODO: Implement

//  private def connect(a: Bits, b: Bits): Bits = {
//    val ret = Wire(Bits(c.encoding.dataWidth.W))
//    val mult = Module(new Multiplier(c.encoding))
//    mult.io.in1 := a
//    mult.io.in2 := b
//    ret := mult.io.out
//    ret
//  }
//
//  for (x <- 0 until simdWidth) {
//    val multiplier = new Multiplier(encoding)
//    io.out.bits(x) := connect(io.in1.bits(x), io.in2.bits(x))
//  }

}
