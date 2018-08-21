package pe.rf

import chisel3._
import chisel3.util._

import pe._

class RFIO(modType: ModType, c: PEConfig) extends Bundle {

  private val exists = c.modExists(modType)

  val toSwitcher: Option[DecoupledIO[Vec[UInt]]] =
    if(exists) modType match {
      case WRF => Some(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))
      case ARF => Some(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))
      case PRF => Some(Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W))))
    } else None

  val frSwitcher: Option[DecoupledIO[Vec[UInt]]] =
    if(exists) modType match {
      case WRF => Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W)))))
      case ARF => Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W)))))
      case PRF => Some(Flipped(Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W)))))
    } else None
}
