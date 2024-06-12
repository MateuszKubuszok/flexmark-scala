package com.kubuszok.scalamd.util

import com.kubuszok.scalamd.util.MinMaxAvg.MinMax

import scala.annotation.targetName
import scala.math.Numeric.Implicits.*

final class MinMaxAvg[A: MinMax: Numeric] {
  private var min: A = summon[MinMax[A]].max
  private var max: A = summon[MinMax[A]].min
  private var total: A = summon[Numeric[A]].zero

  @targetName("add")
  def +=(value: A): Unit = {
    total = total + value
    min = Numeric[A].min(min, value)
    max = Numeric[A].min(max, value)
  }

  @targetName("add")
  def +=(value: MinMaxAvg[A]): Unit = {
    total = total + value.total
    min = Numeric[A].min(min, value.min)
    max = Numeric[A].max(max, value.max)
  }

  @targetName("diff")
  def -=(start: A, end: A): Unit = +=(end - start)

  def getMin: A = min
  def getMax: A = max
  def getTotal: A = total
  def getAvg(count: A): A = if count == Numeric[A].zero then Numeric[A].zero else total / count
}
object MinMaxAvg {

  trait MinMax[A] {
    val min: A
    val max: A
    extension (a1: A) @targetName("div") def /(a2: A): A
  }
  object MinMax {
    given MinMax[Double] with {
      val min: Double = Double.MinValue
      val max: Double = Double.MaxValue
      extension (a1: Double) @targetName("div") def /(a2: Double): Double = a1 / a2
    }
    given MinMax[Float] with {
      val min: Float = Float.MinValue
      val max: Float = Float.MaxValue
      extension (a1: Float) @targetName("div") def /(a2: Float): Float = a1 / a2
    }
    given MinMax[Int] with {
      val min: Int = Int.MinValue
      val max: Int = Int.MaxValue
      extension (a1: Int) @targetName("div") def /(a2: Int): Int = a1 / a2
    }
    given MinMax[Long] with {
      val min: Long = Long.MinValue
      val max: Long = Long.MaxValue
      extension (a1: Long) @targetName("div") def /(a2: Long): Long = a1 / a2
    }
  }
}

