package procelem

import procelem.enums._

// Necessary because passing the PE itself as a parameter defies Module() wraps
// Workaround *possible* with by-name-parameters

/** All the necessary configurations for a PE
  *
  * @param componentList the desired components, given by their ComponentType code
  * @param memSize the desired size of each register file
  * @param simd the SIMD channel and subchannel width
  * @param encoding the data encoding style; e.g. INT8 or FP32
  */
case class Config(componentList: List[ComponentType],
                  memSize: Map[RFType, Int],
                  simd: (Int, Int),
                  encoding: EncodingType) {

  val leftExtant: List[ComponentType with LeftType] = PE.leftComponents intersect componentList
  val rightExtant: List[ComponentType with RightType] = PE.rightComponents intersect componentList
  val allExtant: List[ComponentType] = (leftExtant union rightExtant).distinct

  val nLSources: Int = leftExtant.size
  val nRSources: Int = rightExtant.size
  val nLSinks: Int = leftExtant.map(PE.numInputPorts).sum
  val nRSinks: Int = rightExtant.map(PE.numInputPorts).sum

  val nLPorts: Int = PE.leftPorts.size
  val nRPorts: Int = PE.rightPorts.size

  val n: Int = simd._1
  val m: Int = simd._2
}
