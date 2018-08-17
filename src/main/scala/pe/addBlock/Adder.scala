package pe.addBlock

import chisel3._
import pe.Encoding

class Adder(encoding: Encoding) extends BlackBox {
  //noinspection TypeAnnotation
  val io = IO(new Bundle {
    val in1 = Input(Bits(encoding.dataWidth.W))
    val in2 = Input(Bits(encoding.dataWidth.W))
    val out = Output(Bits(encoding.dataWidth.W))
  })
}
