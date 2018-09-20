package procelem

import chisel3._

import components._
import enums._


class PE(config: Config) extends Module {

  val io: PEIO = IO(new PEIO(config))

  val switcher = Module(new Switcher(config))
  switcher.io.programWire := io.programWire

  if
}

/**
  * Wraps all of the static constants for the PE. Also provides a functional
  * interface for PE generation.
  */
object PE {

  /** List of components that are n * m wide; i.e., pre-flattened */
  val leftComponents: List[ComponentType with LeftType] = List(LP0, LP1, LP2, WRF, ARF, MBL, ABL)

  /** List of components that are n wide; i.e., post-flattened */
  val rightComponents: List[ComponentType with RightType] = List(ABL, PRF, NBL, RP0, RP1, RP2)

  /** List of all components */
  val allComponents: List[ComponentType] = (leftComponents union rightComponents).distinct

  /** List of ports that are n * m wide */
  val leftPorts: List[IOPort with LeftType] = List(LP0, LP1, LP2)

  /** List of ports that are n wide */
  val rightPorts: List[IOPort with RightType] = List(RP0, RP1, RP2)

  /** List of all ports */
  val allPorts: List[IOPort] = (leftPorts union rightPorts).distinct

  /** List of Register Files n * m wide */
  val leftRFs: List[RFType with LeftType] = List(WRF, ARF)

  /** List of Register Files that are n wide */
  val rightRFs: List[RFType with RightType] = List(PRF)

  /** List of all Register Files */
  val allRFs: List[RFType] = (leftRFs union rightRFs).distinct

  /** Gives the number of input ports a component has */
  def numInputPorts(cp: ComponentType): Int = cp match {
    case MBL => 2
    case ABL => 2
    case _ => 1
  }
}
