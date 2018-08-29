package pe

sealed trait TEncoding {
  def dataWidth: Int
}

case object INT8 extends TEncoding { val dataWidth = 8 }
case object INT16 extends TEncoding { val dataWidth = 16 }
case object INT32 extends TEncoding { val dataWidth = 32 }
case object INT64 extends TEncoding { val dataWidth = 64 }

case object FP8 extends TEncoding { val dataWidth = 8 }
case object FP16 extends TEncoding { val dataWidth = 16 }
case object FP32 extends TEncoding { val dataWidth = 32 }
case object FP64 extends TEncoding { val dataWidth = 64 }