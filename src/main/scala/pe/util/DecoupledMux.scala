package pe.util

import chisel3._
import chisel3.util._

/**
  * Multiplexes between Decoupled Interfaces, as the Standard
  * Library does not support bi-directional multiplexing.
  * @param width the number of inputs to the multiplexer
  * @param gen the Chisel type of data wrapped by the Decoupled
  * @tparam A the Scala type of the data
 */
class DecoupledMux[A <: Data](width: Int, gen: A) extends Module {
  //noinspection TypeAnnotation
  val io = IO(new Bundle {
    val in = Vec(width, Flipped(Decoupled(gen)))
    val sel = Input(UInt(log2Ceil(width).W))
    val out = Decoupled(gen)
  })
  io.in.foreach(_.ready := false.B)
  io.out <> io.in(io.sel)
}

/**
  * Provides a wrapper and functional interface for DecoupledMux
  */
object DecoupledMux {

  /**
    * Provides a wrapper for generating a DecoupledMux that
    * returns the output.
    * @param width the number of inputs to the DecoupledMux
    * @param gen the Chisel type of the Decoupled data
    * @tparam A the Scala type of the Decoupled data
    * @return the output of the DecoupledMux
    */
  def apply[A <: Data](width: Int, gen: A): DecoupledIO[A] = {
    new DecoupledMux(width, gen).io.out
  }

  /**
    * Provides a functional interface for the DecoupledMux.
    * Takes the select signal and input signal as inputs
    * and outputs the selected input.
    * @param sel selects which input to output
    * @param elts the inputs to select from
    * @tparam A the Scala type of the Decoupled data
    * @return the selected signal
    */
  def apply[A <: Data](sel: UInt, elts: Vec[A]): DecoupledIO[A] = {
    // TODO: elts.head is inelegant to say the least. Should be replaced with reflections(?).
    val mux = new DecoupledMux(elts.size, elts.head)
    mux.io.in <> elts
    mux.io.sel := sel
    mux.io.out
  }
}