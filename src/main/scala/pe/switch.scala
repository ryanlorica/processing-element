package pe

import chisel3._
import chisel3.util._

class SwitchConfig extends Bundle {
  val configure = Bool()
  val destIn = UInt(3.W)
}

class Switch(c: PEConfig, id: Int) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {

    val config: SwitchConfig = Input(new SwitchConfig)

    val frSwitchNet: Vector[DecoupledIO[Vec[Bits]]] = Vector((0 until 7).map { x: Int =>
      if (c.modExists(x)) Some(Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))) else None })

    val toSwitchNet: Vector[DecoupledIO[Vec[Bits]]] = Vector((0 until 7).map { x: Int =>
      if (c.modExists(x)) Some(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))) else None })

    val frMod: DecoupledIO[Vec[Bits]] = Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))
    val toMod: DecoupledIO[Vec[Bits]] = Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))
  })
}

//val frIORouter: DecoupledIO[Vec[Bits]] =
//Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))
//val frWeightRFRouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.weightRFExists && id != 1) Some(Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))) else None
//val frActvtnRFRouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.actvtnRFExists && id != 2) Some(Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))) else None
//val frMultBlockRouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.multBlockExists && id != 3) Some(Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))) else None
//val frAddBlockRouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.addBlockExists && id != 4) Some(Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))) else None
//val frPsumRFRouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.psumRFExists && id != 5) Some(Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))) else None
//val frNLURouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.nluExists && id != 6) Some(Flipped(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W))))) else None

//val toIORouter: DecoupledIO[Vec[Bits]] =
//Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))
//val toWeightRFRouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.actvtnRFExists && id != 1) Some(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))) else None
//val toActvtnRFRouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.actvtnRFExists && id != 2) Some(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))) else None
//val toPsumRFRouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.psumRFExists && id != 3) Some(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))) else None
//val toAddBlockRouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.addBlockExists && id != 4) Some(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))) else None
//val toMultBlockRouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.multBlockExists && id != 5) Some(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))) else None
//val toNLURouter: Option[DecoupledIO[Vec[Bits]]] =
//if (c.nluExists && id != 6) Some(Decoupled(Vec(c.simdWidth, Bits(c.dataWidth.W)))) else None
