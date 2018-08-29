package pe

import chisel3._
import chisel3.util._

// This is a standardized building block for module interfaces

class Parcel(parcelType: TParcel, c: PEConfig) extends Bundle {

  val thickIn: Option[Vec[DecoupledIO[Vec[Vec[UInt]]]]] = parcelType match {
    case ThickParcel => Some(Flipped(Vec(1, Decoupled(Vec(c.simdN, Vec(c.simdM, Bits(c.encoding.dataWidth.W)))))))
    case AdderBParcel => Some(Flipped(Vec(2, Decoupled(Vec(c.simdN, Vec(c.simdM, Bits(c.encoding.dataWidth.W)))))))
    case MultBParcel => Some(Flipped(Vec(2, Decoupled(Vec(c.simdN, Vec(c.simdM, Bits(c.encoding.dataWidth.W)))))))
    case ThickInPortParcel => Some(Flipped(Vec(1, Decoupled(Vec(c.simdN, Vec(c.simdM, Bits(c.encoding.dataWidth.W)))))))
    case _ => None
  }

  val thinIn: Option[Vec[DecoupledIO[Vec[UInt]]]] = parcelType match {
    case ThinParcel => Some(Flipped(Vec(1, Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W))))))
    case ThinInPortParcel => Some(Flipped(Vec(1, Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W))))))
    case _ => None
  }

  val thickOut: Option[Vec[DecoupledIO[Vec[Vec[UInt]]]]] = parcelType match {
    case ThickParcel => Some(Vec(1, Decoupled(Vec(c.simdN, Vec(c.simdM, Bits(c.encoding.dataWidth.W))))))
    case AdderBParcel => Some(Vec(1, Decoupled(Vec(c.simdN, Vec(c.simdM, Bits(c.encoding.dataWidth.W))))))
    case MultBParcel => Some(Vec(1, Decoupled(Vec(c.simdN, Vec(c.simdM, Bits(c.encoding.dataWidth.W))))))
    case ThickOutPortParcel => Some(Vec(1, Decoupled(Vec(c.simdN, Vec(c.simdM, Bits(c.encoding.dataWidth.W))))))
    case _ => None
  }

  val thinOut: Option[Vec[DecoupledIO[Vec[UInt]]]] = parcelType match {
    case ThinParcel => Some(Vec(1, Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W)))))
    case AdderBParcel => Some(Vec(1, Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W)))))
    case ThinOutPortParcel => Some(Vec(1, Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W)))))
    case ThickParcel => None
  }

}
