package pe.switcher

import chisel3._

class SwitcherCtrl extends Bundle {
  val enable = Bool()
  val sourceIn = UInt(3.W)
  val destIn = UInt(3.W)
}