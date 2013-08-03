package motylwg.camusic

import scala.util.Random
import org.jfugue._

class CaPlayer {
  val pitches = List("C", "C", "C", "G", "G", "D", "D", "A", "A", "E", "F", "B")
  val pitchIndices = Random.shuffle((0 until pitches.size).toList)
  val pitchMap = pitchIndices.zip(pitches).toMap

  val durations = List("w", "h", "q", "i")
  val durationIndices = Random.shuffle((0 until durations.size).toList)
  val durationMap = durationIndices.zip(durations).toMap

  val player = new Player()

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

      pitch + octave + duration
    }

    val notes = ints map note
    notes.mkString(" ")
  }


  def play(ca: Ca) {
    val (voice1, data1) = readBytes(ca.history)
    val (voice2, data2) = readBytes(data1)
    val (voice3, data3) = readBytes(data2)
    val (voice4, data4) = readBytes(data3)

    val ms = "V0 " + musicString(voice1, 3) + " V1 " + musicString(voice2, 4) + " v2 " + musicString(voice3, 5) + " v3 " + musicString(voice4, 6)

    player.play(ms)
  }

  def quit() = {
    player.close()
  }
}