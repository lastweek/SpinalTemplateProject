package apps

import axis._
import spinal.core._
import spinal.lib._


case class AxisSink(dataWidth: Int) extends Component {
  val io = new Bundle {
    val tmpConfig = AxiStreamConfig(dataWidth = dataWidth, keepWidth = dataWidth / 8)
    val in = slave Stream Fragment(AxiStreamPayload(tmpConfig))
  }

  val tdata = UInt(512 bits)
  val tkeep = UInt(64 bits)

  io.in.ready := True

  tdata := 0
  tkeep := 0
  when (io.in.valid) {
    tdata := U(io.in.fragment.tdata)
    tkeep := U(io.in.fragment.tkeep)
  }
}

object AxisSinkGen {
  def main(args: Array[String]) {
    val freq = 250 MHz

    SpinalConfig(
      mode = Verilog,
      targetDirectory = "generated_rtl/apps/",
      defaultClockDomainFrequency = FixedFrequency(freq),
      defaultConfigForClockDomains = ClockDomainConfig(
        clockEdge = RISING,
        resetKind = ASYNC,
        resetActiveLevel = LOW
      )
    ).generate(new AxisSink(dataWidth=512))
  }
}
