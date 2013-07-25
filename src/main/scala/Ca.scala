package motylwg.camusic

import scala.util.Random

class Ca(val rule: Int, val history: List[List[Boolean]]) {
  def this(rule: Int, size: Int) = this(rule, List({
    for (i <- 1 to size) yield Random.nextBoolean()
  }.toList))



  def nextState() = {
    val fun = Rule.getRule(rule)
    val state = history.head

    val first = List(state.head)
    val last = state.takeRight(1).head
    val cyclicState: List[Boolean] = last :: (state ::: first)
    val neighborhoods = cyclicState.sliding(3).toList

    neighborhoods map fun
  }

  def nextCa = new Ca(rule, nextState() :: history)

  def step(n: Int) = (1 to n).toList.foldRight(this)((n, ca) => ca.nextCa)

  override def toString = {
    def rowString(state: List[Boolean]): String = (state map (if (_) "*" else " ")).mkString("|", "", "|").substring(0,40)

    val lines = history.reverse map rowString
    lines.mkString("\n\n", "\n", "\n")
  }

}

object Rule {
  def getRule(rule: Int): List[Boolean] => Boolean = {
    def fun(list: List[Boolean]): Boolean = {
      val n: Int = {
        list match {
          case b3 :: b2:: b1 :: tail =>
            (if (b1) 1 else 0) +
            (if (b2) 2 else 0) +
            (if (b3) 4 else 0)
          case _ => throw new IllegalArgumentException("incorrect neighborhood for rule")
        }
      }
      val mask = 1 << n
      (mask & rule) == mask
    }
    fun
  }
}

