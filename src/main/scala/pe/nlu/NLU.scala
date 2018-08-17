package pe.nlu

import chisel3._
import pe.Encoding

class NLU(dataWidth: Int, encoding: Encoding) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {
    val fSel = Input(UInt(2.W))
    val in = Input(Bits(dataWidth.W))
    val out = Output(Bits(dataWidth.W))
  })

  io.out := 0.U
}