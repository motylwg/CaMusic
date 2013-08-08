package motylwg.camusic.test

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import motylwg.camusic._

@RunWith(classOf[JUnitRunner])
class PlayerTest extends FunSuite {
  test("readBytes") {
    val data = List(
      List(true, true, true, true, false, false, false, false, true, true),
      List(false, false, false, false, true, true, true, true, false, false))
    val pm = new PitchMapper(MinorPitches)
    val pitchMap = pm.getShuffledMap()

    val dm = new DurationMapper(BalancedDurations)
    val durationMap = dm.getShuffledMap()

    val source = new CaSource(pitchMap, durationMap)
    assert(source.readBytes(data) === (List(0xf0, 0x0f), List(List(true, true), List(false, false))))
  }
  /*
  test("musicString") {
    val ints = List(0, 1, 2, 3)
    val caPlayer = new CaPlayer()
    assert(caPlayer.musicString(ints) === "C5i G5w C5h D5q")
  }
  */
}
