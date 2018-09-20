package procelem.enums

sealed trait ComponentType
sealed trait LeftType
sealed trait RightType

sealed trait RFType extends ComponentType
case object WRF extends RFType with LeftType
case object ARF extends RFType with LeftType
case object PRF extends RFType with RightType

sealed trait FuncBloc extends ComponentType
case object MBL extends FuncBloc with LeftType
case object ABL extends FuncBloc with LeftType with RightType
case object NBL extends FuncBloc with RightType

sealed trait IOPort extends ComponentType
case object LP0 extends IOPort with LeftType
case object LP1 extends IOPort with LeftType
case object LP2 extends IOPort with LeftType
case object RP0 extends IOPort with RightType
case object RP1 extends IOPort with RightType
case object RP2 extends IOPort with RightType
