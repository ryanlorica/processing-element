package procelem.components

import chisel3.Module

import procelem._
import procelem.enums._
import procelem.interfaces._

class RegFileL(config: Config) extends Module {
  val io = new LeftIO(1, config)
}
