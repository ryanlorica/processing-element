package pe

sealed trait Encoding {
  def dataWidth: Int
}
case object INT8 extends Encoding { val dataWidth = 8 }
case object INT16 extends Encoding { val dataWidth = 16 }
case object INT32 extends Encoding { val dataWidth = 32 }
case object INT64 extends Encoding { val dataWidth = 64 }

case object FP8 extends Encoding { val dataWidth = 8 }
case object FP16 extends Encoding { val dataWidth = 16 }
case object FP32 extends Encoding { val dataWidth = 32 }
case object FP64 extends Encoding { val dataWidth = 64 }