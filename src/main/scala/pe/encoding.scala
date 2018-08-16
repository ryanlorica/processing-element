package pe

sealed trait Encoding
case object INT8 extends Encoding
case object INT16 extends Encoding
case object INT32 extends Encoding
case object INT64 extends Encoding
case object FP8 extends Encoding
case object FP16 extends Encoding
case object FP32 extends Encoding
case object FP64 extends Encoding