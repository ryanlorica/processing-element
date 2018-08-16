package pe.multBlock

import chisel3.{Bits, BlackBox, Bundle, Input, Output}

class Multiplier(dataWidth: Int) extends BlackBox {
  val io = IO(new Bundle {
    val in1 = Input(Bits(dataWidth.W))
    val in2 = Input(Bits(dataWidth.W))
    val out = Output(Bits(dataWidth.W))
  })
}
