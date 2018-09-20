package procelem

import chisel3._
import chisel3.util._


class ProgramIO(config: Config) extends Bundle {

  override def cloneType: ProgramIO.this.type = new ProgramIO(config).asInstanceOf[this.type]

  val enable = Input(Bool())
  val leftProgram = Input(Vec(config.nLSinks, UInt(log2Ceil(config.nLSources).W)))
  val rightProgram = Input(Vec(config.nRSinks, UInt(log2Ceil(config.nRSources).W)))
}
