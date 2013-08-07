package motylwg.camusic

import scala.util.Random

abstract class PitchList
case object MajorPitches extends PitchList
case object MinorPitches extends PitchList
case object AtonalPitches extends PitchList

class PitchMap(pitchList: PitchList) {

  val pitches = pitchList match {
    case MajorPitches => List("C", "C", "C", "G", "G", "D", "D", "A", "A", "E", "F", "B")
    case MinorPitches => List("C", "C", "C", "G", "G", "D", "D", "A", "A", "Eb", "F", "Bb")
    case AtonalPitches => List("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")

  }

  val pitchIndices = Random.shuffle((0 until pitches.size).toList)
  pitchIndices.zip(pitches).toMap
}
