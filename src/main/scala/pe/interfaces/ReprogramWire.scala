package pe.interfaces

import chisel3._
import pe.PEConfiguration

/**
  * Reprograms the Switcher connections. Contains two separate wires:
  * one for the left address space and one for the right.
  * @param c The configuration of the context PE
  */
class ReprogramWire(c: PEConfiguration) extends Bundle {
  val leftReproWire = Vec(c.leftExtantMods.size, UInt(c.leftAddrWidth.W))
  val rightReproWire = Vec(c.rightExtantMods.size, UInt(c.rightAddrWidth.W))
}

object ReprogramWire {
  def apply(c: PEConfiguration): ReprogramWire = {
    new ReprogramWire(c)
  }
}
