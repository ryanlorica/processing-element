package pe

case class PEConfig(modExists: Vector[Boolean],
                    encoding: Encoding,
                    simdWidth: Int,
                    weightRFSize: Int = 0,
                    actvtnRFSize: Int = 0,
                    psumRFSize: Int = 0)