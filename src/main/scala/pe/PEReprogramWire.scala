package pe

import chisel3._

class PEReprogramWire(c: PEConfiguration) extends Bundle {

  override def cloneType: PEReprogramWire.this.type = new PEReprogramWire(c).asInstanceOf[this.type]

  val modThickSource: Seq[Vec[UInt]] = for (mod <- PE.mods) yield {
    val size: Int = if (c.modExists(mod)) PE.thickSourceSizeOf(mod) else 0
    Input(Vec(size, UInt(c.addrWidth.W)))
  }

  val modThinSource: Seq[Vec[UInt]] = for (mod <- PE.mods) yield {
    val size = if (c.modExists(mod)) PE.thinSourceSizeOf(mod) else 0
    Input(Vec(size, UInt(c.addrWidth.W)))
  }

  val peArraySource: Vec[UInt] = Input(Vec(c.numThickOut + c.numThinOut, UInt(c.addrWidth.W)))
}