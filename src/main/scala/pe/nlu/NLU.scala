package pe.nlu

import chisel3._
import chisel3.util._

import pe._

class NLU(c: PEConfig) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {
    val fSel = Input(UInt(2.W))
    val data = new Parcel(ThinParcel, true, true, c)
  })

  // TODO: Implement ReLu
  // TODO: Implement Max
  // TODO: Implement sinh
  // TODO: Implement tanh

  io.data.out.get := 0.U
}