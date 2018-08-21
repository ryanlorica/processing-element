package pe

import chisel3._

import pe.rf._

class PEControl(c: PEConfig) extends Bundle {

  val weightRFCtrl: Option[RFControl] =
    if (c.modExists(WRF)) Some(new RFControl(c.weightRFSize, c.simdM * c.simdN)) else None

  val actvtnRFCtrl: Option[RFControl] =
    if (c.modExists(ARF)) Some(new RFControl(c.actvtnRFSize, c.simdM * c.simdN)) else None

  val psumRFCtrl: Option[RFControl] =
    if (c.modExists(PRF)) Some(new RFControl(c.psumRFSize, c.simdN)) else None
}
