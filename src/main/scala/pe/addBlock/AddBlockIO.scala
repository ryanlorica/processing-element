package pe.addBlock

import chisel3._
import chisel3.util._

import pe._

class AddBlockIO(c: PEConfig) extends Bundle {

  private val exists = c.modExists(ABL)
  private val treeEn = c.adderTreeEn && exists
  private val paraEn = c.adderParaEn && exists

  val toSwitcherFrTree: Option[DecoupledIO[Vec[UInt]]] =
    if(treeEn) Some(Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W)))) else None

  val toSwitcherFrPara: Option[DecoupledIO[Vec[UInt]]] =
    if(paraEn) Some(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W)))) else None

  val frSwitcherLeft: Option[DecoupledIO[Vec[UInt]]] =
    if(treeEn || paraEn) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None

  val frSwitcherRight: Option[DecoupledIO[Vec[UInt]]] =
    if(paraEn) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None
}
