package procelem.interfaces

import chisel3._
import chisel3.util._
import procelem._


class LeftIO(nPortsIn: Int, c: Config) extends Bundle {

  val inLeft: Vec[Vec[DecoupledIO[Vec[UInt]]]] =
    Flipped(Vec(nPortsIn, Vec(c.n, Decoupled(Vec(c.m, UInt(c.bitwidth.W))))))

  val outLeft: Vec[DecoupledIO[Vec[UInt]]] = Vec(c.n, Decoupled(Vec(c.m, UInt(c.bitwidth.W))))
}
