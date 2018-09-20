package procelem

import chisel3.Bundle

import procelem.components._

class PEIO(config: Config) extends Bundle {

  override def cloneType: PEIO.this.type = new PEIO(config).asInstanceOf[this.type]

  val programWire = new ProgramIO(config)
  val switcherIO = new SwitcherIO(config)
}
