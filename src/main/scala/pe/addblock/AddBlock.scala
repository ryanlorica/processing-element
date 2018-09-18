package pe.addblock

import chisel3._
import chisel3.util._
import pe._
import pe.types.AdderBParcel
import pe.util.Parcel

class AddBlock(c: PEConfiguration) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {
    val ctrl = Input(new AddBlockCtrl)
    val data = new Parcel(AdderBParcel, c)
  })

  // TODO: Implement

}

object AddBlock {

//  private def connect(a: Bits, b: Bits): Bits = {
//    //TODO: Implement AddBlock.connect
//  }

  private def buildTree(): Unit = {

  }

  private def buildFlat(): Unit = {

  }
}
