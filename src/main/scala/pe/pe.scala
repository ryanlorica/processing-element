package pe

import chisel3._

case class PEConfig(
                     weightRFExists: Boolean,
                     actvtnRFExists: Boolean,
                     psumRFExists: Boolean,
                     multBlockExists: Boolean,
                     addBlockExists: Boolean,
                     nluExists: Boolean,
                     encoding: Encoding,
                     simdWidth: Int
              ) {

  val dataWidth: Int = encoding match {
    case INT8 => 8
    case INT16 => 16
    case INT32 => 32
    case INT64 => 64
    case FP8 => 8
    case FP16 => 16
    case FP32 => 32
    case FP64 => 64
    case _ => 0
  }
}

class PE(c: PEConfig) extends Module {

  val weightRF: Option[RF] = if(c.hasWeightRF) Some(Module(new RF(c))) else None
  val actvtnRF: Option[RF] = if(c.hasActvtnRF) Some(Module(new RF(c))) else None
  val psumRF: Option[RF] = if(c.hasPsumRF) Some(Module(new RF(c))) else None
  val addBlock: Option[Nothing] = if(c.hasAddBlock) Some(Module(new AddBlock(c))) else None
  val multBlock: Option[Nothing] = if(c.hasMultBlock) Some(Module(new MultBlock(c))) else None
}
