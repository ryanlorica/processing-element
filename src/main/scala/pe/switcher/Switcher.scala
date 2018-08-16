package pe.switcher

import chisel3._
import chisel3.util._
import pe.PEConfig

class Switcher(c: PEConfig, id: Int) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {

    val config: SwitcherCtrl = Input(new SwitcherCtrl)

    val frSwitchNet: Vector[DecoupledIO[Vec[Bits]]] = Vector((0 until 7).map { x: Int =>
      if (c.modExists(x)) Some(Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))) else None })

    val toSwitchNet: Vector[DecoupledIO[Vec[Bits]]] = Vector((0 until 7).map { x: Int =>
      if (c.modExists(x)) Some(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))) else None })

    val frMod: DecoupledIO[Vec[Bits]] = Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))
    val toMod: DecoupledIO[Vec[Bits]] = Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))
  })

  val sourceReg = RegInit(0.U(3.W))
  val destReg = RegInit(0.U(3.W))

  when (io.config.enable) {
    sourceReg := io.config.sourceIn
    destReg := io.config.destIn
  }

  io.toMod <> PriorityMux(UIntToOH(sourceReg), io.frSwitchNet)
  io.frMod <> PriorityMux(UIntToOH(destReg), io.toSwitchNet)
}

