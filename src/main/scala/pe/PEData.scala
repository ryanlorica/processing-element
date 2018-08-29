package pe

import chisel3._
import chisel3.util._

class PEData(c: PEConfig) extends Bundle {
  val thickInputs: Vec[Option[Parcel]] = VecInit(Seq.fill(c.numThickIn){Some(new Parcel(ThickInPortParcel, c))})
  val thinInputs: Vec[Option[Parcel]] = VecInit(Seq.fill(c.numThinIn){Some(new Parcel(ThinInPortParcel, c))})
  val thickOutputs: Vec[Option[Parcel]] = VecInit(Seq.fill(c.numThickOut){Some(new Parcel(ThickOutPortParcel, c))})
  val thinOutputs: Vec[Option[Parcel]] = VecInit(Seq.fill(c.numThinOut){Some(new Parcel(ThinOutPortParcel, c))})
}
