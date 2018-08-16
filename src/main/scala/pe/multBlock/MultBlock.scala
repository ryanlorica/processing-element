package pe.multBlock

import chisel3.{Bits, Bool, Bundle, Flipped, Module, Vec}
import chisel3.util.Decoupled
import pe._

class MultBlock(val simdWidth: Int, val dataEncoding: Encoding) extends Module {

  val dataWidth: Int = encoding match {
    case INT8 => 8
    case INT16 => 16
    case INT32 => 32
    case INT64 => 64
    case FP8 => 8
    case FP16 => 16
    case FP32 => 32
    case FP64 => 64
    case _ => 0
  }

  val io = IO(new Bundle {
    val enable = Bool()
    val in1 = Flipped(Decoupled(Vec(simdWidth, Bits(dataWidth.W))))
    val in2 = Flipped(Decoupled(Vec(simdWidth, Bits(dataWidth.W))))
    val out = Decoupled(Vec(simdWidth, Bits(dataWidth.W)))
  })

  for (x <- 0 until simdWidth) {
    val multiplier = new Multiplier()
    io.out.bits(x) :=
  }

}
