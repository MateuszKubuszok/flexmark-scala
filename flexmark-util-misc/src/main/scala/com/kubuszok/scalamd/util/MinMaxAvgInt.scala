package com.kubuszok.scalamd.util

import scala.annotation.targetName

class MinMaxAvgInt {
  private var min: Int = Int.MaxValue
  private var max: Int = Int.MinValue
  private var total: Int = 0

  @targetName("add")
  def +=(value: Int): Unit = {
    total += value
    min = math.min(min, value)
    max = math.max(max, value)
  }

  @targetName("add")
  def +=(value: MinMaxAvgInt): Unit = {
    total += value.total
    min = math.min(min, value.min)
    max = math.max(max, value.max)
  }

  @targetName("diff")
  def -=(start: Int, end: Int): Unit = +=(end - start)

  def getMin: Int = min
  def getMax: Int = max
  def getTotal: Int = total
  def getAvg(count: Int): Int = if count == 0 then 0 else total / count
}
