package pe


sealed trait ModType

case object WRF extends ModType
case object ARF extends ModType
case object MBL extends ModType
case object ABL extends ModType
case object PRF extends ModType
case object NLU extends ModType
