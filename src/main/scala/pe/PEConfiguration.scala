package pe

import chisel3.util._

import pe._

case class PEConfiguration(modExists: Map[TModule, Boolean],
                           memSize: Map[TModule, Int],
                           encoding: TEncoding,
                           simdM: Int,
                           simdN: Int,
                           numThickIn: Int,
                           numThinIn: Int,
                           numThickOut: Int,
                           numThinOut: Int,
                           adderTreeEn: Boolean,
                           adderParaEn: Boolean) {

  val leftExtantMods: List[TModule] = PE.leftMods.filter(modExists(_))
  val rightExtantMods: List[TModule] = PE.rightMods.filter(modExists(_))

  val leftAddrWidth: Int = log2Ceil(leftExtantMods.size)
  val rightAddrWidth: Int = log2Ceil(rightExtantMods.size)
}