package testPackage

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import spinal.lib.fsm._
import spinal.lib.bus.amba4.axi._

/*
 * Demonstrate axi4 usage
 * Check clio
 */
/*
case class testAXI4Top() extends Component {
  val io = new Bundle {
    val axi_x = Axi4(Axi4Config(addressWidth  = 32,
                                dataWidth     = 32,
                                idWidth       = 4))  
    val foo = out UInt(32 bits)
  }

  when (axi_x.aw.valid) {
   foo := 1;
  }
}
*/

/*
 * Demonstrates how to state machine
 * https://spinalhdl.github.io/SpinalDoc-RTD/SpinalHDL/Libraries/fsm.html
 *
 * I really like this way.. neat!
 */
case class testStateMachineTop() extends Component {
  val io = new Bundle {
    val result = out Bool
  }

  val counter = Reg(UInt(8 bits)) init (0) simPublic();

  val fsm = new StateMachine {
    io.result := False

    val state_A = new State with EntryPoint
    val state_B = new State
    val state_C = new State

    state_A
      .whenIsActive (goto(state_B))

    state_B
      .onEntry(counter := 0)
      .whenIsActive {
        counter := counter + 1
        when (counter === 4) {
          goto(state_C)
        }
      }
      .onExit(io.result := True)

      state_C
      .whenIsActive (goto(state_C))
  }
}

/*
 * Demonstrates various misc things
 * I walk through the SpinalHDL doc and grabbed some
 */
class testTop(width: Int) extends Component {    
  val io = new Bundle {    
    /*
     * There will be another default clk
     * How can we make this my_clk as default?
     */
    val my_clk    = in Bool
    val my_resetn = in Bool
    val clear     = in Bool    
    val value     = out UInt(width bits)    
    val full      = out Bool    

    val opcode    = in UInt(32 bits)
    val result    = out UInt(32 bits)
  }    

  val counter = Reg(UInt(width bits)) init(0);
  counter := counter + 1;

  when (io.clear) {
    counter := 0;
  }
    
  io.value := counter;
  io.full  := counter === U(counter.range -> true);

  /*
   * Use a different clock
   */
  val myClockDomain = ClockDomain(
    clock = io.my_clk,
    reset = io.my_resetn,
    config = ClockDomainConfig(
      clockEdge = RISING,
      resetKind = ASYNC,
      resetActiveLevel = LOW
    )
  )

  /*
   * Demonstrates how to use a different clock
   * => always @(posedge my_clk or negedge my_resetn)
   */
  val myClockArea = new ClockingArea(myClockDomain) {
    /*
     * Demonstrate how to use {switch => is + default}
     */
    switch (io.opcode) {
      is (0x1) {
        io.result := io.opcode + 0x100;
      }
      
      is (0x2) {
        io.result := io.opcode + 0x200;
      }

      default {
        io.result := 0xdead;
      }
    }
  }

  /*
   * Demonstrates how to use when/elsewhen/otherwise
   * => always
   */
  val y, x = UInt(4 bits)
  when (x === 1) {
    y := x;
  }.elsewhen (x === 2) {
    y := 2;
  }.otherwise {
    y := 3
  }

  /*
   * Demonstrates how to use regsiters
   * https://spinalhdl.github.io/SpinalDoc-RTD/SpinalHDL/Sequential%20logic/registers.html
   */
  //UInt register of 4 bits
  val reg1 = Reg(UInt(4 bit))

  //Register that samples reg1 each cycle
  val reg2 = RegNext(reg1 + 1)

  //UInt register of 4 bits initialized with 0 when the reset occurs
  val reg3 = RegInit(U"0000")
  reg3 := reg2
  when (reg2 === 5) {
    reg3 := 0xF
  }

  /*
   * Freq and time
   *
   * How did Scala able to do this? Hmm..
   */
  val frequency = 100 MHz
  val timeoutLimit = 3 ms
  val period = 100 us

  /*
   * Assert => $display
   */
  assert(
    assertion = True,
    message = "Test assert",
    severity = ERROR
  );

  /*
   * SpinalHDL Libraries
   */

  /*
   * Demonstrates library: Timeout
   *
   * Really neat!!
   */
  val timeout = Timeout(1 us)
  val aa = Reg(UInt(1 bits));
  when (timeout) {
    timeout.clear()
    aa := 1
  }
}

object top_module {
  def main(args: Array[String]) {
    val freq = 100 MHz

    /*
     * Use SpinalConfig to specify
     * the various configurations.
     *
     * See: https://spinalhdl.github.io/SpinalDoc-RTD/SpinalHDL/Other%20language%20features/vhdl_generation.html
     */
    /*
     * Alternatively, we can also just generate using one line
     * => SpinalVerilog(new testStateMachineTop())
     */
    SpinalConfig(
      mode = Verilog,
      targetDirectory = "generated_rtl",
      defaultClockDomainFrequency = FixedFrequency(freq),
      defaultConfigForClockDomains = ClockDomainConfig(
        clockEdge = RISING,
        resetKind = ASYNC,
        resetActiveLevel = LOW
      )
    ).generate(new testTop(width=4))

    /*
     * Generate the StateMachine module
     */
    SpinalConfig(
      mode = Verilog,
      targetDirectory = "generated_rtl",
      defaultClockDomainFrequency = FixedFrequency(freq),
      defaultConfigForClockDomains = ClockDomainConfig(
        clockEdge = RISING,
        resetKind = ASYNC,
        resetActiveLevel = LOW
      )
    ).generate(new testStateMachineTop)


/*
    SpinalConfig(
      mode = Verilog,
      targetDirectory = "generated_rtl",
      defaultClockDomainFrequency = FixedFrequency(freq),
      defaultConfigForClockDomains = ClockDomainConfig(
        clockEdge = RISING,
        resetKind = ASYNC,
        resetActiveLevel = LOW
      )
    ).generate(new testAXI4Top)
*/
  }
}
