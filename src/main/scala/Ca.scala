package motylwg.camusic

import scala.util.Random

class Ca(val rule: Int, val history: List[List[Boolean]]) {
  def this(rule: Int, size: Int) = this(rule, List({
    for (i <- 1 to size) yield Random.nextBoolean()
  }.toList))

  // efficiency is not an issue for this project so go for simplicity and clarity
  def nextState() = {
    val update = Rule(rule)
    val state = history.head

    val first = List(state.head)
    val last = state.takeRight(1).head
    val cyclicState: List[Boolean] = last :: state ::: first
    val neighborhoods = cyclicState.sliding(3).toList

    neighborhoods map update
  }

  def nextCa = new Ca(rule, nextState() :: history)

  def step(n: Int) = (1 to n).toList.foldRight(this)((_, ca) => ca.nextCa)

  override def toString = {
    def rowString(state: List[Boolean]): String = (state map (if (_) "*" else " ")).mkString("").substring(1,60)

    val lines = history.reverse map rowString
    lines.mkString("\n\n", "\n", "\n")
  }
}

object Rule {
  def apply(rule: Int): List[Boolean] => Boolean = {
    def update(list: List[Boolean]): Boolean = {
      val n: Int = {
        list match {
          case b3 :: b2 :: b1 :: tail =>
            (if (b1) 1 else 0) +
            (if (b2) 2 else 0) +
            (if (b3) 4 else 0)
          case _ => throw new IllegalArgumentException("insufficient neighborhood for rule")
        }
      }
      val mask = 1 << n
      (mask & rule) == mask
    }
    update
  }
}

