package pe.switcher

import chisel3._
import pe._

class SwitcherCtrl(c: PEConfig) extends Bundle {

  val modThickSource: Vec[Option[Vec[UInt]]] = Input(VecInit(for(mod <- c.mods) yield {
    if (c.modExists(mod)) mod match {
      case WRF => Some(Vec(1, UInt(c.addrWidth.W)))
      case ARF => Some(Vec(1, UInt(c.addrWidth.W)))
      case MBL => Some(Vec(2, UInt(c.addrWidth.W)))
      case ABL => Some(Vec(2, UInt(c.addrWidth.W)))
      case PRF => None
      case NLU => None
    } else None
  }))

  val modThinSource: Vec[Option[Vec[UInt]]] = Input(VecInit(for(mod <- c.mods) yield {
    if (c.modExists(mod)) mod match {
      case WRF => None
      case ARF => None
      case MBL => None
      case ABL => None
      case PRF => Some(Vec(1, UInt(c.addrWidth.W)))
      case NLU => Some(Vec(1, UInt(c.addrWidth.W)))
    } else None
  }))

  val extSource: Vec[Option[Vec[UInt]]] = Input(VecInit(
    Seq.fill(c.numThickOut + c.numThinOut){Some(Vec(1, 0.U(c.addrWidth.W)))}
  ))

}