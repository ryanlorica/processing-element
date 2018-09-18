package pe.switcher

import chisel3._
import chisel3.util._
import pe._
import pe.interfaces.ModIO
import pe.util.DecoupledMux

import scala.collection.immutable

class Switcher(c: PEConfiguration) extends Module {
  //noinspection TypeAnnotation
  val io = new Bundle {
    val reprogramEn = Input(Bool())
    val reprogramWire = Input(ReprogramWire(c))
    val toLeftMods: Vec[ModIO] = VecInit(c.leftExtantMods.map(ModIO(_, c)))
    val toRightMods: Vec[ModIO] = VecInit(c.rightExtantMods.map(ModIO(_, c)))
  }
  private val programReg = ReprogramWire(c)
  when (io.reprogramEn) { programReg <> io.reprogramWire }
  (io.toLeftMods zip programReg.leftReproWire).foreach { case (mod: ModIO, sel: UInt) =>
    mod <> DecoupledMux[ModIO](sel, io.toLeftMods)}
  (io.toRightMods zip programReg.rightReproWire).foreach { case (mod: ModIO, sel: UInt) =>
    mod <> DecoupledMux[ModIO](sel, io.toRightMods)}
}
