package pe.util

import chisel3._
import chisel3.util._
import pe._

// This is a standardized building block for module interfaces

class Parcel(parcelType: TParcel, c: PEConfiguration) extends Bundle {

  override def cloneType: Parcel.this.type = new Parcel(parcelType, c).asInstanceOf[this.type]

  private val thickInElem: DecoupledIO[Vec[Vec[UInt]]] =
    Flipped(Decoupled(Vec(c.simdN, Vec(c.simdM, Bits(c.encoding.dataWidth.W)))))
  val thickIn: Vec[DecoupledIO[Vec[Vec[UInt]]]] = Output(Vec(Parcel.thickInSizeOf(parcelType), thickInElem))

  private val thinInElem: DecoupledIO[Vec[UInt]] =
    Flipped(Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W))))
  val thinIn: Vec[DecoupledIO[Vec[UInt]]] = Output(Vec(Parcel.thinInSizeOf(parcelType), thinInElem))

  private val thickOutElem: DecoupledIO[Vec[Vec[UInt]]] =
    Decoupled(Vec(c.simdN, Vec(c.simdM, Bits(c.encoding.dataWidth.W))))
  val thickOut: Vec[DecoupledIO[Vec[Vec[UInt]]]] = Input(Vec(Parcel.thickOutSizeOf(parcelType), thickOutElem))

  private val thinOutElem: DecoupledIO[Vec[UInt]] =
    Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W)))
  val thinOut: Vec[DecoupledIO[Vec[UInt]]] = Input(Vec(Parcel.thinOutSizeOf(parcelType), thinOutElem))
}

object Parcel {
  val thickInSizeOf: Map[TParcel, Int] = Map(
    ThickParcel -> 1,
    ThinParcel -> 0,
    MultBParcel -> 2,
    AdderBParcel -> 2,
    ThickInPortParcel -> 1,
    ThinInPortParcel -> 0,
    ThickOutPortParcel -> 0,
    ThinOutPortParcel -> 0,
    NullParcel -> 0
  )
  val thinInSizeOf: Map[TParcel, Int] = Map(
    ThickParcel -> 0,
    ThinParcel -> 1,
    MultBParcel -> 0,
    AdderBParcel -> 0,
    ThickInPortParcel -> 1,
    ThinInPortParcel -> 0,
    ThickOutPortParcel -> 0,
    ThinOutPortParcel -> 0,
    NullParcel -> 0
  )
  val thickOutSizeOf: Map[TParcel, Int] = Map(
    ThickParcel -> 1,
    ThinParcel -> 0,
    MultBParcel -> 1,
    AdderBParcel -> 1,
    ThickInPortParcel -> 0,
    ThinInPortParcel -> 0,
    ThickOutPortParcel -> 1,
    ThinOutPortParcel -> 0,
    NullParcel -> 0
  )
  val thinOutSizeOf: Map[TParcel, Int] = Map(
    ThickParcel -> 0,
    ThinParcel -> 1,
    MultBParcel -> 0,
    AdderBParcel -> 1,
    ThickInPortParcel -> 0,
    ThinInPortParcel -> 0,
    ThickOutPortParcel -> 0,
    ThinOutPortParcel -> 1,
    NullParcel -> 0
  )
}
