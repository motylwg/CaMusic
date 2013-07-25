package motylwg.camusic.test

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import motylwg.camusic._

@RunWith(classOf[JUnitRunner])
class CaTest extends FunSuite {
  test("CA constructed with initial state") {
    val ca = new Ca(110,32)
    assert(ca.history.size === 1)
    assert(ca.history.head.size === 32)
  }

  test("rule 256 behavior") {
    val rule256 = Rule.getRule(0xff)
    assert(rule256(List(true, true, true)) === true)
    assert(rule256(List(true, true, false)) === true)
  }

  test("rule 16 behavior") {
    val rule16 = Rule.getRule(0x0f)
    assert(rule16(List(false, false, false)) === true)
    assert(rule16(List(false, true, true)) === true)
    assert(rule16(List(true, false, false)) === false)
    assert(rule16(List(true, true, true)) === false)

  }
}



