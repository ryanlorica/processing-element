package pe.switcher

import chisel3._
import chisel3.util._
import pe._

class Switcher(c: PEConfig, id: Int) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {

    val config: SwitcherCtrl = Input(new SwitcherCtrl)

    val toArray: Vec[DecoupledIO[Vec[UInt]]] =
      Vec(c.numRFs, Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W)))))

    val frArray: Vec[DecoupledIO[Vec[UInt]]] =
      Vec(c.numRFs, Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))

    val weightRF: SwitcherInterface = new SwitcherInterface(WRF, c)
    val actvtnRF: SwitcherInterface = new SwitcherInterface(ARF, c)
    val multBlock: SwitcherInterface = new SwitcherInterface(MBL, c)
    val

    val toActvtnRF: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(ARF)) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None

    val frActvtnRF: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(ARF)) Some(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W)))) else None

    val toMultLeft: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(MBL)) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None

    val toMultRight: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(MBL)) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None

    val frMult: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(MBL)) Some(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W)))) else None

    val toAddLeft: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(ABL)) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None

    val toAddRight: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(ABL)) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None

    val frAddPara: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(ABL)) Some(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W)))) else None

    val frAddTree: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(ABL)) Some(Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W)))) else None

    val toPsumRF: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(PRF)) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None

    val frPsumRF: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(PRF)) Some(Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W)))) else None

    val toNLU: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(NLU)) Some(Flipped(Decoupled(Vec(c.simdM * c.simdN, Bits(c.encoding.dataWidth.W))))) else None

    val frNLU: Option[DecoupledIO[Vec[UInt]]] =
      if(c.modExists(NLU)) Some(Decoupled(Vec(c.simdN, Bits(c.encoding.dataWidth.W)))) else None
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

