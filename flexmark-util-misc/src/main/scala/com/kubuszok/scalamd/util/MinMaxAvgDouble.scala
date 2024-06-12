package com.kubuszok.scalamd.util

import scala.annotation.targetName

class MinMaxAvgDouble {
  private var min: Double = Double.MaxValue
  private var max: Double = Double.MinValue
  private var total: Double = 0.0

  @targetName("add")
  def +=(value: Double): Unit = {
    total += value
    min = math.min(min, value)
    max = math.max(max, value)
  }

  @targetName("add")
  def +=(value: MinMaxAvgDouble): Unit = {
    total += value.total
    min = math.min(min, value.min)
    max = math.max(max, value.max)
  }

  @targetName("diff")
  def -=(start: Double, end: Double): Unit = +=(end - start)

  def getMin: Double = min
  def getMax: Double = max
  def getTotal: Double = total
  def getAvg(count: Double): Double = if count == 0.0 then 0.0 else total / count
}
