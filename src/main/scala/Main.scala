package motylwg.camusic

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits._
import scala.swing._
import scala.swing.event._

object Main extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "CaMusic"
    object quitButton extends Button {
      text = "Quit"
      reactions += {
        case ButtonClicked(_) => quit()
      }
    }
    val playButton = new Button {
      text = "Play"
      reactions += {
        case ButtonClicked(_) => {
          val myFuture = future {
            play()
          }
        }
      }

    }
    contents = new FlowPanel {
      contents.append(playButton)
      contents.append(quitButton)
    }

    size = new Dimension(200, 75)

    def play() = {
      CaPlayer.quit()
      val initCa = new Ca(110, 1000)
      val ca = initCa.step(720)

      println(ca)

      CaPlayer.play(ca)
    }
  }




}
