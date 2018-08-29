package pe.addblock

import chisel3._
import chisel3.util._

import pe._

class AddBlock(c: PEConfig) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {
    val ctrl = Input(new AddBlockCtrl)
    val data = new AddBlockIO(c)
  })

  // TODO: Implement

}
