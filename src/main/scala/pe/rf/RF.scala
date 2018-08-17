package pe.rf

import chisel3.core.DontCare
import chisel3.util.log2Ceil
import chisel3._


import scala.math.pow

class RF(val memSize: Int, val dataWidth: Int, val simdWidth: Int) extends Module {

  private val addrWidth: Int = log2Ceil(memSize)

  //noinspection TypeAnnotation
  val io = IO(new Bundle{
    val control = Input(new RFControl(memSize, simdWidth))
    val in = Input(Vec(simdWidth, Bits(dataWidth.W)))
    val out = Output(Vec(simdWidth, Bits(dataWidth.W)))
  })

  val regs = RegInit(VecInit(Seq.fill(pow(2, addrWidth).toInt){0.U(dataWidth.W)}))

  when (io.control.wEnable) {
    for (i <- 0 until simdWidth) { regs(io.control.wAddr(i)) := io.in(i) }
  }

  when (io.control.rEnable) {
    io.out := VecInit(for(x <- 0 until simdWidth) yield { regs(io.control.rAddr(x)) })
  } .otherwise {
    io.out := DontCare
  }

}
