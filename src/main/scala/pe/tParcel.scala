package pe

sealed trait TParcel

case object ThickParcel extends TParcel
case object ThinParcel extends TParcel
case object MultBParcel extends TParcel
case object AdderBParcel extends TParcel
case object ThickInPortParcel extends TParcel
case object ThickOutPortParcel extends TParcel
case object ThinInPortParcel extends TParcel
case object ThinOutPortParcel extends TParcel
