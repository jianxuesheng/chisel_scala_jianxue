import Chisel._

class MMax2 extends Module {
  val io = new Bundle {
    val in0 = UInt(INPUT,  8)
    val in1 = UInt(INPUT,  8)
    val out = UInt(OUTPUT, 8)
    val out1 = UInt(OUTPUT, 8)
  }

  val out1 = Reg(init = 0.U)
  out1 := Mux(io.in0 > io.in1, io.in0, io.in1)
  io.out1 := out1
  io.out := Mux(io.in0 > io.in1, io.in0, io.in1)
  
}

class Max2Tests(c: MMax2) extends Tester(c) {
  for (i <- 0 until 10) {
    // FILL THIS IN HERE
    val in0 = rnd.nextInt(1 << 8)
    val in1 = rnd.nextInt(1 << 8)
    poke(c.io.in0, in0)
    poke(c.io.in1, in1)
    // FILL THIS IN HERE
    step(1)
    expect(c.io.out, if(in0 > in1) in0 else in1)
    expect(c.io.out1, if(in0 > in1) in0 else in1)
  }
}

object max2 {
  def main(args: Array[String]) : Unit={
      val margs=Array("--backend","v","--compile") 
      chiselMain(margs, () => Module(new MMax2())) 
      val margs1=Array("--backend","c","--genHarness","--compile","--test", "--vcd")
      chiselMainTest(margs1, () => Module(new MMax2())){c => new Max2Tests(c)}
  }
}