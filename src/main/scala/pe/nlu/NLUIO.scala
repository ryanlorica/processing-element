package pe.nlu

import chisel3._
import chisel3.util._

import pe._

class NLUIO(c: PEConfig) {

  private val exists = c.modExists(NLU)

  val toSwitcher: Option[DecoupledIO[Vec[UInt]]] =
    if(exists) Some(Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W)))) else None

  val frSwitcher: Option[DecoupledIO[Vec[UInt]]] =
    if(exists) Some(Flipped(Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W))))) else None
}
