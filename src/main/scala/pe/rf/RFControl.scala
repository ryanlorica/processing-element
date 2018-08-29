package pe.rf

import chisel3._
import chisel3.util._

// TODO: Change the SIMD parameters here.

class RFControl(memSize: Int, simdWidth: Int) extends Bundle {

  override def cloneType: RFControl.this.type = new RFControl(memSize, simdWidth).asInstanceOf[this.type]

  private val addrWidth = log2Ceil(memSize)

  val wEnable = Bool()
  val rEnable = Bool()
  val wAddr = Vec(simdWidth, UInt(addrWidth.W))
  val rAddr = Vec(simdWidth, UInt(addrWidth.W))
}
