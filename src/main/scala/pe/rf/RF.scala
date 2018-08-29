package pe.rf

import chisel3._
import chisel3.util._

import pe._

import scala.math.pow

class RF(modType: TModule, c: PEConfig) extends Module {

  private val addrWidth: Int = log2Ceil(c.memSize(modType))

  //noinspection TypeAnnotation
  val io = IO(new Bundle{
    // TODO: Add control
    val data = modType match {
      case WRF => new Parcel(ThickParcel, c)
      case ARF => new Parcel(ThickParcel, c)
      case PRF => new Parcel(ThinParcel, c)
    }
  })

  val regs = RegInit(VecInit(Seq.fill(pow(2, addrWidth).toInt){0.U(c.encoding.dataWidth.W)}))

  // TODO: Re-implement RF

  /*
  when (io.control.wEnable) {
    for (i <- 0 until simdWidth) { regs(io.control.wAddr(i)) := io.in(i) }
  }

  when (io.control.rEnable) {
    io.out := VecInit(for(x <- 0 until simdWidth) yield { regs(io.control.rAddr(x)) })
  } .otherwise {
    io.out := DontCare
  }
  *

}
