package motylwg.camusic

import org.jfugue._


object CaPlayer {
  def readBytes(data: List[List[Boolean]]) = {

    def readBits(bits: List[Boolean], acc: Int, n: Int): (Int, List[Boolean]) = {
      n match {
        case 0 => (acc, bits)
        case _ => readBits(bits.tail, (acc << 1) + (if (bits.head) 1 else 0), n - 1)
      }
    }

    def readByte(bits: List[Boolean]) = readBits(bits, 0, 8)

    (data map readByte).unzip
  }

  def musicString(ints: List[Int], octave: Int = 5) = {
    def term(n: Int) = {
      val note = (65 + ((n >> 2) % 12)).toChar match {
        case 'H' => 'C'
        case 'I' => 'G'
        case 'J' => 'F'
        case 'K' => 'B'
        case 'L' => 'C'
        case c => c
      }

      val duration = (n % 4) match {
        case 0 => "w"
        case 1 => "h"
        case 2 => "q"
        case _ => "i"
      }
      note + octave.toString + duration
    }
    val terms = ints map term
    terms.mkString(" ")
  }


  def play(ca: Ca) {
    val player = new Player()

    val (voice1, data1) = readBytes(ca.history.reverse)
    val (voice2, data2) = readBytes(data1)
    val (voice3, data3) = readBytes(data2)
    val (voice4, data4) = readBytes(data3)

    val ms = "V0 " + musicString(voice1, 3) + " V1 " + musicString(voice2, 4) + " v2 " + musicString(voice3, 5) + " v3 " + musicString(voice4, 6)



    println(ms)
    player.play(ms)
  }
}