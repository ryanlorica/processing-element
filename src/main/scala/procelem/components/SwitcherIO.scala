package procelem.components

import chisel3._
import chisel3.util._

import procelem._

class SwitcherIO(config: Config) extends Bundle {

  override def cloneType: SwitcherIO.this.type = new SwitcherIO(config).asInstanceOf[this.type]

  private val bitwidth = config.encoding.bitwidth

  val programWire = new ProgramIO(config)

  val leftDataSource: Vec[Vec[DecoupledIO[Vec[UInt]]]] =
    Flipped(Vec(config.leftExtant.size, Vec(config.n, Decoupled(Vec(config.m, UInt(bitwidth.W))))))

  val rightDataSource: Vec[Vec[DecoupledIO[UInt]]] =
    Flipped(Vec(config.rightExtant.size, Vec(config.n, Decoupled(UInt(bitwidth.W)))))

  val leftDataSink: Vec[Vec[DecoupledIO[Vec[UInt]]]] =
    Vec(config.nLSinks, Vec(config.n, Decoupled(Vec(config.m, UInt(bitwidth.W)))))

  val rightDataSink: Vec[Vec[DecoupledIO[UInt]]] =
    Vec(config.nRSinks, Vec(config.n, Decoupled(UInt(bitwidth.W))))
}
