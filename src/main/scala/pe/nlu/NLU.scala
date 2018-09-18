package pe.nlu

import chisel3._
import chisel3.util._
import pe._
import pe.types.ThinParcel
import pe.util.Parcel

class NLU(c: PEConfiguration) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {
    val fSel = Input(UInt(2.W))
    val data = new Parcel(ThinParcel, c)
  })

  // TODO: Implement ReLu
  // TODO: Implement Max
  // TODO: Implement sinh
  // TODO: Implement tanh


}

object NLU {
  // stuff
}