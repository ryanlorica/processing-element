package procelem.interfaces

import chisel3._
import chisel3.util._

import procelem._

class RightIO(nPortsIn: Int, c: Config) extends Bundle {

  val inRight: Vec[Vec[DecoupledIO[UInt]]] =
    Flipped(Vec(nPortsIn, Vec(c.n, Decoupled(UInt(c.bitwidth.W)))))

  val outRight: Vec[DecoupledIO[UInt]] = Vec(c.n, Decoupled(UInt(c.bitwidth.W)))
}
