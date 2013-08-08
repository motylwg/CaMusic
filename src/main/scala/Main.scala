package motylwg.camusic

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits._
import scala.swing._
import scala.swing.event._

import org.jfugue._


object Main extends SimpleSwingApplication {
  var stopped = true
  val pm = new PitchMapper(MajorPitches)
  val dm = new DurationMapper(BalancedDurations)

  val pitchMap = pm.getShuffledMap
  val durationMap = dm.getShuffledMap
  val player = new Player()
  var CaPlayer = new CaSource(pitchMap, durationMap)

  def play()  {
    if (player.isPlaying) player.stop()

    val initCa = new Ca(110, 1000)
    val ca = initCa.step(25)

    val pitchMap = pm.getShuffledMap
    val durationMap = dm.getShuffledMap
    val source = new CaSource(pitchMap, durationMap)

    val f = future {
      player.play(source.read(ca))
    }
    f onComplete {_ => if (!stopped) play}
  }


  def top = new MainFrame {
    title = "CaMusic"

    object stopButton extends Button {
      text = "Stop"
      reactions += {
        case ButtonClicked(_) => {
          player.stop()
          stopped = true
        }
      }
    }

    object playButton extends Button {
      text = "Play"
      reactions += {
        case ButtonClicked(_) => {
          if (player.isStarted) player.stop()
          play
          stopped = false
        }
      }
    }

    contents = new FlowPanel {
      contents.append(playButton)
      contents.append(stopButton)
    }

    size = new Dimension(200, 75)

  }




}
