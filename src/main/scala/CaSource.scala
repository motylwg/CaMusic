package motylwg.camusic



class CaSource(val pitchMap: Map[Int, String], val durationMap: Map[Int, String]) {


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
    def note(n: Int) = {

      val pitch = pitchMap(n % pitchMap.size)

      val duration = durationMap(n % durationMap.size)

      pitch + octave.toString + duration
    }

    val notes = ints map note
    notes.mkString(" ")
  }


  def read(ca: Ca) = {
    val (voice1, data1) = readBytes(ca.history)
    val (voice2, data2) = readBytes(data1)
    val (voice3, data3) = readBytes(data2)
    val (voice4, data4) = readBytes(data3)

    "V0 " + musicString(voice1, 3) + " V1 " + musicString(voice2, 4) + " v2 " + musicString(voice3, 5) + " v3 " + musicString(voice4, 6)
  }



}