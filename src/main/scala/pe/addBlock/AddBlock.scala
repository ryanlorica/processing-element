package pe.addBlock

import chisel3.util.Decoupled
import chisel3._
import pe.Encoding

import scala.collection.immutable
import scala.math.floor

class AddBlock(val encoding: Encoding, val simdWidth: Int) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {
    val ctrl = Input(new AddBlockCtrl)
    val in = Flipped(Decoupled(Vec(simdWidth, Bits(encoding.dataWidth.W))))
    val out = Decoupled(Vec(floor(simdWidth / 2).toInt, Bits(encoding.dataWidth.W)))
  })

  // This whole connect/buildTree structure is hideous.
  // Once we know what the FP support will look like,
  // we should change this.
  private def buildTree[A](xs: List[A], op: (A, A) => A): A = xs match {
    case List(single) => single
    case default =>
      val grouped = default.grouped(2).toList
      val result = for (g <- grouped) yield { g match {
        case List(a, b) => op(a, b)
        case List(x) => x
      }}
      buildTree(result, op)
  }

  private def connect(a: Bits, b: Bits): Bits = {
    val ret = Wire(Bits(encoding.dataWidth.W))
    val adder = Module(new Adder(encoding))
    adder.io.in1 := a
    adder.io.in2 := b
    ret := adder.io.out
    ret
  }

  when (io.ctrl.reduce) {
    io.out.bits(0) := buildTree(io.in.bits.toList, connect)
  } .otherwise {
    val grouped: Seq[List[UInt]] = io.in.bits.toList.grouped(2).toList
    io.out.bits := VecInit(for (g: immutable.Seq[UInt] <- grouped) yield { connect(g.head, g.last) })
  }

}
