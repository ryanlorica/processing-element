package pe.multBlock

import chisel3._
import chisel3.util._

import pe._

class MultBlockIO(c: PEConfig) extends Bundle {

  private val exists = c.modExists(MBL)

  val toSwitcher: Option[DecoupledIO[Vec[UInt]]] =
    if(exists) Some(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W)))) else None

  val frSwitcherLeft: Option[DecoupledIO[Vec[UInt]]] =
    if(exists) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None

  val frSwitcherRight: Option[DecoupledIO[Vec[UInt]]] =
    if(exists) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None
}
