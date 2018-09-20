package procelem.components

import chisel3._
import chisel3.util._

import procelem._

/** Connects the various components together based on reprogrammable registers
  *
  * @param config the configuration of the parent PE
  */
class Switcher(config: Config) extends Module {

  val io: SwitcherIO = IO(new SwitcherIO(config))

  private val programReg = Reg(new ProgramIO(config))
  programReg := io.programWire

  io.leftDataSource.foreach(_.foreach(_.ready := false.B))
  io.rightDataSource.foreach(_.foreach(_.ready := false.B))

  for ((component, sel) <- io.leftDataSink zip programReg.leftProgram) {
    component <> io.leftDataSource(sel)
  }
  for ((component, sel) <- io.rightDataSink zip programReg.rightProgram) {
    component <> io.rightDataSource(sel)
  }
}
