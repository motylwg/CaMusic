package motylwg.camusic

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits._
import akka.actor.Actor

import scala.concurrent.duration._

case object Start
case object Stop

class CaActor extends Actor {
  var caPlayer = new CaPlayer()

  def receive = {
    case Start => {
      val f = future {
        caPlayer.stop()
        caPlayer = new CaPlayer()
        val initCa = new Ca(110, 512)
        val ca = initCa.step(256)
        caPlayer.play(ca)
      }
    }
    case Stop => {
      caPlayer.stop()
    }
  }
}
