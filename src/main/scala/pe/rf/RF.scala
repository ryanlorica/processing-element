package pe.rf

import chisel3.core.DontCare
import chisel3.util.log2Ceil
import chisel3.{Bits, Bundle, Input, Module, Output, RegInit, Vec, VecInit, when}
import pe.RFControl

import scala.math.pow

class RF(val memSize: Int, val dataWidth: Int, val simdWidth: Int) extends Module {

  private val addrWidth: Int = log2Ceil(memSize)

  //noinspection TypeAnnotation
  val io = IO(new Bundle{
    val control = Input(new RFControl(addrWidth))
    val in = Input(Vec(simdWidth, Bits(dataWidth.W)))
    val out = Output(Vec(simdWidth, Bits(dataWidth.W)))
  })

  val regs = RegInit(VecInit(Seq.fill(pow(2, c.addrWidth).toInt){0.U(c.dataWidth.W)}))

  when (io.control.wEnable) {
    for (i <- 0 until simdWidth) { regs(io.control.wAddr(i)) := io.in(i) }
  }

  when (io.control.rEnable) {
    for (i <- 0 until simdWidth) { io.out(i) := regs(io.control.rAddr(i)) }
  } .otherwise {
    io.out := DontCare
  }

}
