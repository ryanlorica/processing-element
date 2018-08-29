package pe

import chisel3._
import chisel3.util.log2Ceil
import pe.rf._
import pe.addblock._
import pe.multblock._
import pe.switcher._

class PE(c: PEConfig) extends Module {

  val io: Record = IO(new Bundle {
    val ctrl = Input(new PEControl(c))
  })
}

object PE {

  def apply(c: PEConfig): PE = new PE(c)

  val mods: List[TModule] = List(WRF, ARF, MBL, ABL, PRF, NLU)
  val modID: Map[TModule, Int] = Map(WRF -> 0, ARF -> 1, MBL -> 2, ABL -> 3, PRF -> 4, NLU -> 5)
}