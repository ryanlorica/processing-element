package pe.util

import chisel3._
import chisel3.util._

import pe._

/**
  * A standardized interface between modules. Each module's input consists of
  * a number of ports, which in turn consist of channels and subchannels.
  * Each port gets its own configuration register; i.e., it can pull from an
  * independent source. Channels and subchannels are for SIMD processing.
  *
  * Each module only gets one output port, but will match channel/subchannel size
  * of the input ports.
  *
  * The channels and subchannels will only connect to channels and subchannels
  * of the same index within the port. I.e., the 2nd subchannel of the 3rd channel
  * can only connect to the 2nd subchannel of the 3rd channel of another module.
  * If one attempts to connect an input to an output which has less channels than
  * it does, the remaining channels on the input will be Don't Cares.
  *
  * @param modType The type of module automatically sets the shape
  * @param c The configuration object for the PE
  */
class ModIO(modType: TModule, c: PEConfiguration) extends Bundle {
  private val numChannels = modType match {
    case WRF => c.simdN
    case ARF => c.simdN
    case ABL => c.simdN
    case MBL => c.simdN
    case PRF => 1
    case NBL => 1
  }
  private val numSubchannels = c.simdM
  /**
    * The input to each module. Consists of ports > channels > subchannels. Ports are separate inputs.
    * Channels are the number of vectors to simultaneously process. Subchannels are the number of
    * dimensions of each vector to simultaneously process.
    */
  val in: Vec[DecoupledIO[Vec[Vec[UInt]]]] =
    Vec(ModIO.numInPorts(modType), Decoupled(Vec(numChannels, Vec(numSubchannels, UInt(c.encoding.dataWidth.W)))))
  /**
    * The output of each module; each module can only have one. Channels are the number of vectors to simultaneously
    * process. Subchannels are the number of dimensions of each vector to simultaneously process
    */
  val out: DecoupledIO[Vec[Vec[UInt]]] =
    Decoupled(Vec(numChannels, Vec(numSubchannels, UInt(c.encoding.dataWidth.W))))
}

object ModIO {
  /**
    * Generates the IO necessary for a module, given the type
    * @param modType The type of the module
    * @param c The configuration of the context PE
    * @return
    */
  def apply(modType: TModule, c: PEConfiguration): ModIO = {
    new ModIO(modType, c)
  }
  /** Hardcoding the number of ports each module has */
  val numInPorts: Map[TModule, Int] = Map(
    WRF -> 1,
    ARF -> 1,
    ABL -> 2,
    MBL -> 2,
    PRF -> 1,
    NBL -> 1,
  )
}
