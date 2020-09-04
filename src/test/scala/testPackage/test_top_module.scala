package testPackage

import org.scalatest._

import spinal.core._
import spinal.core.sim._

class test_top_module extends FunSuite {
    println("OUt");

    SimConfig.withWave.compile(new testStateMachineTop).doSim { dut =>
      dut.clockDomain.forkStimulus(10)

      for (i <- 0 to 3) {
        dut.clockDomain.waitSampling()
        println(dut.io.result)
      }
    }
}
