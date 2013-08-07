package motylwg.camusic

//import akka.actor.SupervisorStrategy.Stop
import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }

import scala.concurrent.duration._

case object Start
case object Stop

class CaActor extends Actor {
  //var ca = new Ca(110, 512)
  val caPlayer = new CaPlayer()

  def receive = {
    case Start => {
      val initCa = new Ca(110, 512)
      val ca = initCa.step(256)
      caPlayer.play(ca)
    }
  }
}
