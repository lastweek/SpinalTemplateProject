# SpinalHDL Template Project

These files are adapted from SpinalWorkshop.


## TODO

- how to do simulation
- how to know the performance of generated RTL?
   - II?
   - frequency limit?
   - do we have to use Vivado to know?
- how to build FIFOs to chain internal functions?
- how to use AXI-Stream/AXI4 to talk to outside?
- how to use BRAM/UltraRAM?
- how to use external DRAM properly, i.e., how to use axi-stream/axi4 properly?

## Simulation

1. add test file to `src/test/scala/testPackage/*.scala`.
2. within `sbt`, run `test`.
3. TODO: fill the scala file, how to deal with multiple DUTs? (DUT is the one object passed to generateVerilog, i guess)

- Install Verilator and several Jave dev package before starts
- Within simulation code, if we want to access variable within a DUT,
  that various should be marked as `simPublic()`.
  See here: https://spinalhdl.github.io/SpinalDoc-RTD/SpinalHDL/Simulation/signal.html.
