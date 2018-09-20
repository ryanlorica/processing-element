package procelem.enums

sealed trait EncodingType {
  def bitwidth: Int
}

case object INT8 extends EncodingType { val bitwidth = 8 }
case object INT16 extends EncodingType { val bitwidth = 16 }
case object INT32 extends EncodingType { val bitwidth = 32 }
case object INT64 extends EncodingType { val bitwidth = 64 }

case object FP8 extends EncodingType { val bitwidth = 8 }
case object FP16 extends EncodingType { val bitwidth = 16 }
case object FP32 extends EncodingType { val bitwidth = 32 }
case object FP64 extends EncodingType { val bitwidth = 64 }
