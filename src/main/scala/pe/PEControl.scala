package pe

import chisel3._

import pe.rf._

class PEControl(c: PEConfig) extends Bundle {

  private val IO = 0
  private val WEIGHTRF = 1
  private val ACTVTNRF = 2
  private val MULTBLOCK = 3
  private val ADDBLOCK = 4
  private val PSUMRF = 5
  private val NLU = 6

  val weightRFCtrl: Option[RFControl] =
    if (c.modExists(WEIGHTRF)) Some(new RFControl(c.weightRFSize, c.simdWidth)) else None

  val actvtnRFCtrl: Option[RFControl] =
    if (c.modExists(ACTVTNRF)) Some(new RFControl(c.actvtnRFSize, c.simdWidth)) else None

  val psumRFCtrl: Option[RFControl] =
    if (c.modExists(PSUMRF)) Some(new RFControl(c.psumRFSize, c.simdWidth)) else None
}
