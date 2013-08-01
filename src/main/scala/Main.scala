package motylwg.camusic

/**
 * Created with IntelliJ IDEA.
 * User: bill
 * Date: 7/31/13
 * Time: 8:09 PM
 * To change this template use File | Settings | File Templates.
 */
object Main extends  App {
  val initCa = new Ca(110, 1000)
  val ca = initCa.step(1024)

  println(ca)

  CaPlayer.play(ca)
}
