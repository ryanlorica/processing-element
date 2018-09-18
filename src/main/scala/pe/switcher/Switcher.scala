package pe.switcher

import chisel3._
import chisel3.util._
import pe._
import pe.util.Parcel

import scala.collection.immutable


class Switcher(c: PEConfiguration) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {
    val reprogramEn: Bool = Input(Bool())
    val reprogramWire: PEReprogramWire = new PEReprogramWire(c)
    val toPEArray: PEDataWire = new PEDataWire(c)
    val toInternalModule: Seq[Parcel] = for (mod <- PE.mods) yield {
      if(c.modExists(mod)) mod match {
        case WRF => Flipped(new Parcel(ThickParcel, c))
        case ARF => Flipped(new Parcel(ThickParcel, c))
        case MBL => Flipped(new Parcel(MultBParcel, c))
        case ABL => Flipped(new Parcel(AdderBParcel, c))
        case PRF => Flipped(new Parcel(ThinParcel, c))
        case NBL => Flipped(new Parcel(ThinParcel, c))
      } else {
        Flipped(new Parcel(NullParcel, c))
      }
    }
  })

  private val programReg: PEReprogramWire = Reg(new PEReprogramWire(c))
  when(io.reprogramEn) { programReg <> io.reprogramWire }

  private val totalSource: Seq[Parcel] =
    io.toInternalModule ++ io.toPEArray.thickInputs ++ io.toPEArray.thinInputs

  private val toPEArrayProgrammingReg: Vec[UInt] = programReg.peArraySource
  private val totalToPEArray: IndexedSeq[Parcel] = io.toPEArray.thickOutputs ++ io.toPEArray.thinOutputs
  private val totalSourceThickIn: Seq[DecoupledIO[Vec[Vec[UInt]]]] =
    totalSource.filter(_.thickIn.nonEmpty).map(_.thickIn.head)
  private val totalSourceThinIn: Seq[DecoupledIO[Vec[UInt]]] =
    totalSource.filter(_.thinIn.nonEmpty).map(_.thinIn.head)
  private val indexedTotalToPEArray: (IndexedSeq[(Parcel, Int)], IndexedSeq[(Parcel, Int)]) =
    totalToPEArray.zipWithIndex.splitAt(io.toPEArray.thickOutputs.size)
  indexedTotalToPEArray._1.foreach{ case (toPEArray: Parcel, i: Int) =>
    toPEArray.thickOut.head := PriorityMux(UIntToOH(toPEArrayProgrammingReg(i)), totalSourceThickIn)}
  indexedTotalToPEArray._2.foreach{ case (toPEArray: Parcel, i: Int) =>
    toPEArray.thinOut.head := PriorityMux(UIntToOH(toPEArrayProgrammingReg(i)), totalSourceThinIn)}

  private val moduleThickProgReg: Seq[Vec[UInt]] = programReg.modThickSource
  private val moduleThinProgReg: Seq[Vec[UInt]] = programReg.modThinSource
  private val totalSourceThickOut: Seq[DecoupledIO[Vec[Vec[UInt]]]] = totalSource.map(_.thickOut.head)
  private val totalSourceThinOut: Seq[DecoupledIO[Vec[UInt]]] = totalSource.map(_.thinOut.head)
  private val extantModules: immutable.Seq[TModule] = PE.mods.filter(c.modExists(_))
  extantModules.foreach(extantModule => {
    moduleThickProgReg(PE.modID(extantModule)).indices.foreach { i: Int =>
      io.toInternalModule(PE.modID(extantModule)).thickIn(i) :=
        PriorityMux(UIntToOH(moduleThickProgReg(PE.modID(extantModule))(i)), totalSourceThickOut)}
    moduleThinProgReg(PE.modID(extantModule)).indices.foreach { i: Int =>
      io.toInternalModule(PE.modID(extantModule)).thinIn(i) :=
        PriorityMux(UIntToOH(moduleThinProgReg(PE.modID(extantModule))(i)), totalSourceThinOut)}
  })
}

