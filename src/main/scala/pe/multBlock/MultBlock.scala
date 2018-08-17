package pe.multBlock

import chisel3._
import chisel3.util._

import pe._

class MultBlock(val simdWidth: Int, val encoding: Encoding) extends Module {

  val io = IO(new Bundle {
    val enable = Bool()
    val in1 = Flipped(Decoupled(Vec(simdWidth, Bits(encoding.dataWidth.W))))
    val in2 = Flipped(Decoupled(Vec(simdWidth, Bits(encoding.dataWidth.W))))
    val out = Decoupled(Vec(simdWidth, Bits(encoding.dataWidth.W)))
  })

  private def connect(a: Bits, b: Bits): Bits = {
    val ret = Wire(Bits(encoding.dataWidth.W))
    val mult = Module(new Multiplier(encoding))
    mult.io.in1 := a
    mult.io.in2 := b
    ret := mult.io.out
    ret
  }

  for (x <- 0 until simdWidth) {
    val multiplier = new Multiplier(encoding)
    io.out.bits(x) := connect(io.in1.bits(x), io.in2.bits(x))
  }

}
