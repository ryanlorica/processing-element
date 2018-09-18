package pe.interfaces

import chisel3._
import pe.PEConfiguration
import pe.rf._
import pe.types.{ARF, PRF, WRF}

class PEControl(c: PEConfiguration) extends Bundle {

  val weightRFCtrl: Option[RFControl] =
    if (c.modExists(WRF)) Some(new RFControl(c.memSize(WRF), c.simdM * c.simdN)) else None

  val actvtnRFCtrl: Option[RFControl] =
    if (c.modExists(ARF)) Some(new RFControl(c.memSize(ARF), c.simdM * c.simdN)) else None

  val psumRFCtrl: Option[RFControl] =
    if (c.modExists(PRF)) Some(new RFControl(c.memSize(PRF), c.simdN)) else None
}
