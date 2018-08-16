package pe

import chisel3._
import chisel3.util._

import scala.math.pow

class RFControl(memsize: Int, simdWidth: Int) extends Bundle {
  private val addrWidth = log2Ceil(memsize)

  val configure = Bool()
  val destIn = UInt()

  val wEnable = Bool()
  val rEnable = Bool()
  val wAddr = Vec(simdWidth, UInt(addrWidth.W))
  val rAddr = Vec(simdWidth, UInt(addrWidth.W))
}

class RF(val memsize: Int, val encoding: Encoding, val simdWidth: Int) extends Module {

  private val addrWidth: Int = log2Ceil(memsize)
  val dataWidth: Int = encoding match {
    case INT8 => 8
    case INT16 => 16
    case INT32 => 32
    case INT64 => 64
    case FP8 => 8
    case FP16 => 16
    case FP32 => 32
    case FP64 => 64
    case _ => 0
  }

  //noinspection TypeAnnotation
  val io = IO(new Bundle{
    val control = Input(new RFControl(addrWidth))
    val in = Input(Vec(simdWidth, Bits(dataWidth.W)))
    val out = Output(Vec(simdWidth, Bits(dataWidth.W)))
  })

  val regs = RegInit(
    VecInit(
      Seq.fill(pow(2, c.addrWidth).toInt){0.U(c.dataWidth.W)}
    )
  )

  when (io.control.wEnable) {
    for (i <- 0 until simdWidth) {
      regs(io.control.wAddr(i)) := io.in(i)
    }
  }

  when (io.control.rEnable) {
    for (i <- 0 until simdWidth) {
      io.out(i) := regs(io.control.rAddr(i))
    }
  } .otherwise {
    io.out(i) := DontCare
  }

}
