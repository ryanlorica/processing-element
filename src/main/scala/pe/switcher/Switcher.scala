package pe.switcher

import chisel3._
import chisel3.util._

import pe.PEConfig

import scala.collection.immutable

class Switcher(c: PEConfig, id: Int) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {

    val config: SwitcherCtrl = Input(new SwitcherCtrl)

    val frSwitchNet: immutable.IndexedSeq[Option[DecoupledIO[Vec[UInt]]]] = (0 until 7).map { x: Int =>
      if (c.modExists(x)) Some(Flipped(Decoupled(Vec(c.simdWidth, Bits(c.encoding.dataWidth.W))))) else None }

    val toSwitchNet: immutable.IndexedSeq[Option[DecoupledIO[Vec[UInt]]]] = (0 until 7).map { x: Int =>
      if (c.modExists(x)) Some(Decoupled(Vec(c.simdWidth, Bits(c.encoding.dataWidth.W)))) else None }

    val frMod: DecoupledIO[Vec[UInt]] = Flipped(Decoupled(Vec(c.simdWidth, UInt(c.encoding.dataWidth.W))))
    val toMod: DecoupledIO[Vec[UInt]] = Decoupled(Vec(c.simdWidth, UInt(c.encoding.dataWidth.W)))
  })

  val sourceReg = RegInit(0.U(3.W))
  val destReg = RegInit(0.U(3.W))

  when (io.config.enable) {
    sourceReg := io.config.sourceIn
    destReg := io.config.destIn
  }

  io.toMod <> PriorityMux(UIntToOH(sourceReg).toBools, io.frSwitchNet.map( x => x.getOrElse(DontCare)))
  io.frMod <> PriorityMux(UIntToOH(destReg).toBools, io.toSwitchNet.map( x => x.getOrElse(DontCare)))
}

