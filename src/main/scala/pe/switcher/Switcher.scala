package pe.switcher

import chisel3._
import chisel3.util._

import pe._


class Switcher(c: PEConfig) extends Module {

  //noinspection TypeAnnotation
  val io = IO(new Bundle {

    val configEn: Bool = Input(Bool())
    val config: SwitcherCtrl = new SwitcherCtrl(c)

    val peIO: PEData = new PEData(c)

    val modIO: Vec[Option[Parcel]] = VecInit(for (mod <- PE.mods) yield {
      if(c.modExists(mod)) mod match {
        case WRF => Flipped(new Parcel(ThickParcel, c))
        case ARF => Flipped(new Parcel(ThickParcel, c))
        case MBL => Flipped(new Parcel(MultBParcel, c))
        case ABL => Flipped(new Parcel(AdderBParcel, c))
        case PRF => Flipped(new Parcel(ThinParcel, c))
        case NLU => Flipped(new Parcel(ThinParcel, c))
    }})

  })

  val configReg: SwitcherCtrl = Reg(new SwitcherCtrl(c))
  when(io.configEn) {
    configReg <> io.config
  }

  val modThickSourceReg: Vec[Option[Vec[UInt]]] = configReg.modThickSource
  val modThinSourceReg: Vec[Option[Vec[UInt]]] = configReg.modThinSource
  val extSourceReg: Vec[Option[Vec[UInt]]] = configReg.extSource

  val source: Vec[Option[Parcel]] = VecInit(io.modIO ++ io.peIO.thickInputs ++ io.peIO.thinInputs)

  (io.peIO.thickOutputs ++ io.peIO.thinOutputs).zipWithIndex.foreach {
    case (sink: Option[Parcel], i: Int) =>
      val sel: Seq[Bool] = UIntToOH(extSourceReg(i).get(1)).toBools()
      if (i < io.peIO.thickOutputs.size)
        sink.get.thickIn.get(1) := PriorityMux[Option[Parcel]](sel zip source).get.thickIn.get(1)
      else
        sink.get.thinIn.get(1) := PriorityMux[Option[Parcel]](sel zip source).get.thinIn.get(1)
  }

  PE.mods.filter(c.modExists(_)).foreach(sink => {
    for (i: Int <- modThickSourceReg(PE.modID(sink)).getOrElse(Seq.empty[Option[Vec[UInt]]]).indices) {
      val sel: Seq[Bool] = UIntToOH(modThickSourceReg(PE.modID(sink)).get(i)).toBools()
      io.modIO(PE.modID(sink)).get.thickIn.get(i) := PriorityMux[Option[Parcel]](sel zip source).get.thickIn.get(1)
    }
    for (i: Int <- modThinSourceReg(PE.modID(sink)).getOrElse(Seq.empty[Option[Vec[UInt]]]).indices) {
      val sel: Seq[Bool] = UIntToOH (modThinSourceReg(PE.modID(sink)).get(i)).toBools()
      io.modIO(PE.modID(sink)).get.thinIn.get(i) := PriorityMux[Option[Parcel]](sel zip source).get.thinIn.get(1)
    }
  })
}

