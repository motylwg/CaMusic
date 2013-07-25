package motylwg.camusic

import scala.util.Random

class Ca(val rule: Int, val history: List[List[Boolean]]) {
  def this(rule: Int, size: Int) = this(rule, List( { for(i <- 1 to size) yield Random.nextBoolean() }.toList))

  val fun = Rule.getRule(rule)
  val state = history.head
}

object Rule {
  def getRule(rule: Int): (Boolean, Boolean, Boolean) => Boolean = {
    def fun(b1: Boolean, b2: Boolean, b3: Boolean): Boolean = {
      val n: Int = {
        (if (b1) 4 else 0) +
        (if (b2) 2 else 0) +
        (if (b3) 1 else 0)
      }
      val mask = 1 << n
      (mask & rule) == mask
    }
    fun
  }
}

