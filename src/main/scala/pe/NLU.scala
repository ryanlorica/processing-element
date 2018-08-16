package pe

import chisel3._

class NLU(dataWidth: Int, encoding: Encoding) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {
    val fSel = Input(UInt(2.W))
    val in = Input(Bits(dataWidth.W))
    val out = Output(Bits(dataWidth.W))
  })

  when (io.fSel.idEnable.getOrElse(false.B)) {
    io.out := io.in
  } .elsewhen (io.fSel.reluEnable.getOrElse(false.B)) {
    when (io.in.data > 0.S) {
      io.out := io.in.data
    } .otherwise {
      io.out := 0.U
    }
  } .elsewhen (io.fSel.tanhEnable.getOrElse(false.B)) {
    // TODO
    io.out := 0.U
  } .elsewhen (io.fSel.sinhEnable.getOrElse(false.B)) {
    // TODO
    io.out := 0.U
  } .otherwise {
    io.out := 0.U
  }
}