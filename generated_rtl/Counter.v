// Generator : SpinalHDL v1.3.2    git head : 41815ceafff4e72c2e3a3e1ff7e9ada5202a0d26
// Date      : 02/09/2020, 20:13:30
// Component : Counter


module Counter (
      input   io_my_clk,
      input   io_my_resetn,
      input   io_clear,
      output [3:0] io_value,
      output  io_full,
      input  [31:0] io_opcode,
      output reg [31:0] io_result,
      input   clk,
      input   resetn);
  reg [3:0] counter_1_;
  wire [3:0] _zz_1_;
  reg [3:0] y;
  wire [3:0] x;
  assign io_value = counter_1_;
  assign _zz_1_[3 : 0] = (4'b1111);
  assign io_full = (counter_1_ == _zz_1_);
  always @ (*) begin
    case(io_opcode)
      32'b00000000000000000000000000000001 : begin
        io_result = (io_opcode + (32'b00000000000000000000000100000000));
      end
      32'b00000000000000000000000000000010 : begin
        io_result = (io_opcode + (32'b00000000000000000000001000000000));
      end
      default : begin
        io_result = (32'b00000000000000001101111010101101);
      end
    endcase
  end

  always @ (*) begin
    if((x == (4'b0001)))begin
      y = x;
    end else begin
      if((x == (4'b0010)))begin
        y = (4'b0010);
      end else begin
        y = (4'b0011);
      end
    end
  end

  always @ (posedge clk or negedge resetn) begin
    if (!resetn) begin
      counter_1_ <= (4'b0000);
    end else begin
      counter_1_ <= (counter_1_ + (4'b0001));
      if(io_clear)begin
        counter_1_ <= (4'b0000);
      end
    end
  end

endmodule

