package pe

import chisel3._
import chisel3.util.log2Ceil
import pe.rf._
import pe.addblock._
import pe.multblock._
import pe.switcher._

class PE(c: PEConfiguration) extends Module {

  val io: Record = IO(new Bundle {
    val ctrl = Input(new PEControl(c))
  })
}

object PE {
  def apply(c: PEConfiguration): PE = new PE(c)

  val leftMods = List(LP0, LP1, LP2, WRF, ARF, MBL, ABL)
  val rightMods = List(RP0, RP1, RP2, ABL, PRF, NBL)
  val mods: List[TModule] = leftMods.union(rightMods)
}