package pe.switcher

import chisel3.{Bool, Bundle, UInt}

class SwitcherCtrl extends Bundle {
  val enable = Bool()
  val sourceIn = UInt(3.W)
  val destIn = UInt(3.W)
}