package pe.types

sealed trait ModType

case object WRF extends ModType
case object ARF extends ModType
case object MBL extends ModType
case object ABL extends ModType
case object PRF extends ModType
case object NBL extends ModType
case object LP0 extends ModType
case object LP1 extends ModType
case object LP2 extends ModType
case object RP0 extends ModType
case object RP1 extends ModType
case object RP2 extends ModType
