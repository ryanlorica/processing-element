package pe

sealed trait TModule

case object WRF extends TModule
case object ARF extends TModule
case object MBL extends TModule
case object ABL extends TModule
case object PRF extends TModule
case object NLU extends TModule
