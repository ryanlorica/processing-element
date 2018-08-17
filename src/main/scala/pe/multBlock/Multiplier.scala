package pe.multBlock

import chisel3._

import pe.Encoding

class Multiplier(val encoding: Encoding) extends BlackBox {
  val io = IO(new Bundle {
    val in1 = Input(Bits(encoding.dataWidth.W))
    val in2 = Input(Bits(encoding.dataWidth.W))
    val out = Output(Bits(encoding.dataWidth.W))
  })
}
