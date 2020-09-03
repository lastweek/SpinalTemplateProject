// Generator : SpinalHDL v1.3.2    git head : 41815ceafff4e72c2e3a3e1ff7e9ada5202a0d26
// Date      : 02/09/2020, 17:21:43
// Component : Counter


module Counter (
      input   io_clear,
      output [3:0] io_value,
      output  io_full,
      input   clk,
      input   reset);
  reg [3:0] counter_1_;
  wire [3:0] _zz_1_;
  assign io_value = counter_1_;
  assign _zz_1_[3 : 0] = (4'b1111);
  assign io_full = (counter_1_ == _zz_1_);
  always @ (posedge clk or posedge reset) begin
    if (reset) begin
      counter_1_ <= (4'b0000);
    end else begin
      counter_1_ <= (counter_1_ + (4'b0001));
      if(io_clear)begin
        counter_1_ <= (4'b0000);
      end
    end
  end

endmodule

