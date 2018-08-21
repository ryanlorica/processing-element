package pe

case class PEConfig(modExists: Map[ModType, Boolean],
                    encoding: Encoding,
                    simdM: Int,
                    simdN: Int,
                    weightRFSize: Int = 0,
                    actvtnRFSize: Int = 0,
                    psumRFSize: Int = 0,
                    adderTreeEn: Boolean = false,
                    adderParaEn: Boolean = false) {

  private def btoi(b: Boolean): Int = if(b) 1 else 0

  val numRFs: Int = btoi(modExists(WRF)) + btoi(modExists(ARF)) + btoi(modExists(PRF))
}