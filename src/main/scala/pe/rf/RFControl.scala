package pe.rf

import chisel3.{Bool, Bundle, UInt, Vec}
import chisel3.util.log2Ceil

class RFControl(memSize: Int, simdWidth: Int) extends Bundle {
  private val addrWidth = log2Ceil(memSize)

  val wEnable = Bool()
  val rEnable = Bool()
  val wAddr = Vec(simdWidth, UInt(addrWidth.W))
  val rAddr = Vec(simdWidth, UInt(addrWidth.W))
}
