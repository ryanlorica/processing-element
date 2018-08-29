package pe

import chisel3.util._

import pe._

case class PEConfig(modExists: Map[TModule, Boolean],
                    memSize: Map[TModule, Int],
                    encoding: TEncoding,
                    simdM: Int,
                    simdN: Int,
                    numThickIn: Int,
                    numThinIn: Int,
                    numThickOut: Int,
                    numThinOut: Int,
                    adderTreeEn: Boolean = false,
                    adderParaEn: Boolean = false
                    ) {
  
  //val numRFs: Int = (for (mod <- List(WRF, ARF, PRF)) yield { modExists(mod) }).count( _ == true )
  val addrWidth: Int = log2Ceil(PE.mods.size + numThickIn + numThinIn)
}