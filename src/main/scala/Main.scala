package motylwg.camusic


import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits._
import scala.swing._
import scala.swing.event._
import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }

object Main extends SimpleSwingApplication {

  val system = ActorSystem("CaPlayer")

  val player = system.actorOf(Props[CaActor], "CaPlayer")

  def top = new MainFrame {
    title = "CaMusic"
    object stopButton extends Button {
      text = "Stop"
      reactions += {
        case ButtonClicked(_) => player ! Stop
      }
    }
    val playButton = new Button {
      text = "Play"
      reactions += {
        case ButtonClicked(_) => player ! Start
      }
    }

    contents = new FlowPanel {
      contents.append(playButton)
      contents.append(stopButton)
    }

    size = new Dimension(200, 75)
    /*
    def play() = {
      caPlayer.quit()
      val initCa = new Ca(110, 512)
      val ca = initCa.step(256)

      caPlayer = new CaPlayer()

      caPlayer.play(ca)
    }
    */
  }




}
