package apps

import org.scalatest._
import spinal.core._
import spinal.core.sim._

class TestAxisPassthrough extends FunSuite {

    SimConfig.withWave.compile(new AxisPassthrough).doSim { dut =>
      dut.clockDomain.forkStimulus(10)

      for (i <- 0 to 20) {
        dut.clockDomain.waitSampling()
      }
    }
}

