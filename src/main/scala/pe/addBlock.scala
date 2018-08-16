package pe

import chisel3._

class AddBlockCtrl extends Bundle {
  val enable = Bool()
  val flatten = Bool()
}

class AddBlock[A <: Bits](val dataWidth: Int, val simdWidth: Int) extends Module {

  val io = IO(new Bundle {
    val ctrl = Input(new AddBlockCtrl)
    val in = Input(Vec(simdWidth, A(dataWidth.W)))
    val out = Output(Vec(simdWidth, A(dataWidth.W)))
  })

  private def buildTree[B](xs: List[B], op: (B, B) => B): B = xs match {
    case List(single) => single
    case default =>
      val grouped = default.grouped(2).toList
      val result = for (g <- grouped) yield { g match {
        case List(a, b) => op(a, b)
        case List(x) => x
      }}
      buildTree(result, op)
  }

}
