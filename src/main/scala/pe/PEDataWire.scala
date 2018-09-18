package pe

import chisel3._
import chisel3.util._
import pe.util.Parcel

class PEDataWire(c: PEConfiguration) extends Bundle {
  val thickInputs: Vec[Parcel] = Input(Vec(c.numThickIn, new Parcel(ThickInPortParcel, c)))
  val thinInputs: Vec[Parcel] = Input(Vec(c.numThinIn, new Parcel(ThinInPortParcel, c)))
  val thickOutputs: Vec[Parcel] = Output(Vec(c.numThickOut, new Parcel(ThickOutPortParcel, c)))
  val thinOutputs: Vec[Parcel] = Output(Vec(c.numThinOut, new Parcel(ThinOutPortParcel, c)))
}
