package motylwg.camusic

import scala.util.Random

abstract class MusicSetting

abstract class PitchSetting extends MusicSetting
case object MajorPitches extends PitchSetting
case object MinorPitches extends PitchSetting
case object AtonalPitches extends PitchSetting

abstract class DurationSetting extends MusicSetting
case object BalancedDurations extends DurationSetting
case object FastDurations extends DurationSetting
case object SlowDurations extends DurationSetting
case object SyncompatedDurations extends DurationSetting

trait MusicMapper {
  val entries: List[String]

  def getMap() = ((0 until entries.size).toList).zip(entries).toMap

  def getShuffledMap() = {
    val indices = Random.shuffle((0 until entries.size).toList)
    indices.zip(entries).toMap
  }
}

class PitchMapper(val setting: PitchSetting) extends MusicMapper{
  val entries = setting match {
    case MajorPitches => List("C", "C", "C", "G", "G", "D", "D", "A", "A", "E", "F", "B")
    case MinorPitches => List("C", "C", "C", "G", "G", "D", "D", "A", "A", "Eb", "F", "Bb")
    case AtonalPitches => List("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
  }
}

class DurationMapper(val setting: DurationSetting) extends MusicMapper{
  val entries = setting match {
    case BalancedDurations => List("w", "h", "q", "q", "i", "i")
    case FastDurations => List("h", "q", "i", "s")
    case SlowDurations => List("w", "w", "h", "q")
    case SyncompatedDurations => List("w", "h", "q", "i", "s")
  }
}

