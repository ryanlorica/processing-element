package pe.addBlock

import chisel3.{Bool, Bundle}

class AddBlockCtrl extends Bundle {
  val enable = Bool()
  val reduce = Bool()
}
