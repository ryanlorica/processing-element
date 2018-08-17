package pe

import chisel3._

import pe.rf._
import pe.addBlock._
import pe.multBlock._
import pe.switcher._

class PE(c: PEConfig) extends Module {

  val io = IO(new Bundle {
    val ctrl = Input(new PEControl(c))
  })
}
