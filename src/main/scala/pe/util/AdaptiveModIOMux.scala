package pe.util

import chisel3._
import chisel3.util._
import pe.{PE, PEConfiguration, TModule}

/**
  * Multiplexes ModIOs of different shapes
  * @param sinkMod is the sink module
  * @param c is the configuration of the context PE
  */
class AdaptiveModIOMux(sinkMod: TModule, c: PEConfiguration) extends Module {
  //noinspection TypeAnnotation
  val io = IO(new Bundle{
    val sel: UInt = Input(UInt(3.W))
    // Chisel 3.12 will allow replacing Seq[ModIO] with MixedVec
    val source: Seq[ModIO] = PE.mods.filter(mod => c.modExists(mod) && mod != sinkMod).map(ModIO(_, c))
    val sink: ModIO = ModIO(sinkMod, c)
  })
  io.sink.in.foreach{ port =>

  }
}

object AdaptiveModIOMux {
  /**
    * Provides a wrapper for generating an AdaptiveMux
    * that returns the output
    * @param sinkMod The sink module
    * @param c The configuration of the context PE
    */
  def apply(sinkMod: TModule, c: PEConfiguration): ModIO = {
    new AdaptiveModIOMux(sinkMod, c).io.sink
  }
}
