package pe

import chisel3.util._
import pe._
import pe.types.{Encoding, ModType}

case class PEConfiguration(modExists: Map[ModType, Boolean],
                           memSize: Map[ModType, Int],
                           encoding: Encoding,
                           simdM: Int,
                           simdN: Int,
                           numThickIn: Int,
                           numThinIn: Int,
                           numThickOut: Int,
                           numThinOut: Int,
                           adderTreeEn: Boolean,
                           adderParaEn: Boolean) {

  val leftExtantMods: List[ModType] = PE.leftMods.filter(modExists(_))
  val rightExtantMods: List[ModType] = PE.rightMods.filter(modExists(_))

  val leftAddrWidth: Int = log2Ceil(leftExtantMods.size)
  val rightAddrWidth: Int = log2Ceil(rightExtantMods.size)
}