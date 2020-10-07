package apps

import lib._
import spinal.core._
import spinal.lib._

case class AxisPassthrough(dataWidth: Int) extends Component with RenameIO {
  val io = new Bundle {
    val tmpConfig = AxiStreamConfig(dataWidth = dataWidth, keepWidth = dataWidth / 8)
    val in  = slave Stream Fragment(AxiStreamPayload(tmpConfig))
    val out = master Stream Fragment(AxiStreamPayload(tmpConfig))
  }

  io.out << io.in

  addPrePopTask(renameIO)
}

object AxisPassthroughGen {
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
    ).generate(new AxisPassthrough(dataWidth=512))
  }
}
