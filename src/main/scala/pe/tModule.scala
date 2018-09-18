package pe

sealed trait TModule

case object WRF extends TModule
case object ARF extends TModule
case object MBL extends TModule
case object ABL extends TModule
case object PRF extends TModule
case object NBL extends TModule
case object LP0 extends TModule
case object LP1 extends TModule
case object LP2 extends TModule
case object RP0 extends TModule
case object RP1 extends TModule
case object RP2 extends TModule
