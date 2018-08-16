package pe

import chisel3._

class MultBlock[A <: Bits](val simdWidth: Int, val dataEncoding: Encoding) extends Module {
  val io = IO(new Bundle {
    val enable = Bool()
    val in1 = Input(Vec(simdWidth, A()))
  })
}
