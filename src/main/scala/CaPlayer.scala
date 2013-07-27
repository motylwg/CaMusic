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

  def musicString(ints: List[Int]) = {
    val ints2 = ints map (n => (n % 7) + 65)
    val chars = ints2 map ((c:Int) => c.toChar)
    chars.mkString(" ")
  }


  def play(ca: Ca) {
    val player = new Player()

    val (voice1, data1) = readBytes(ca.history.reverse)
    val (voice2, data2) = readBytes(data1)
    val (voice3, data3) = readBytes(data2)

    val ms = "V0 " + musicString(voice1) + " V1 " + musicString(voice2) + " v2 " + musicString(voice3)



    println(ms)
    player.play(ms)
  }
}