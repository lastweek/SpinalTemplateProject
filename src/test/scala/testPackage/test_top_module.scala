package testPackage

import org.scalatest._

import spinal.core._
import spinal.core.sim._

class test_top_module extends FunSuite {

    SimConfig.withWave.compile(new testStateMachineTop).doSim { dut =>
      dut.clockDomain.forkStimulus(10)

      for (i <- 0 to 20) {
        dut.clockDomain.waitSampling()
        println(dut.counter.toInt)
      }
    }
}
