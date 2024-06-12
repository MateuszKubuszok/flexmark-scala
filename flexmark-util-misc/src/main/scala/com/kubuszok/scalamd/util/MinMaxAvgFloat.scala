package com.kubuszok.scalamd.util

import scala.annotation.targetName

class MinMaxAvgFloat {
  private var min: Float = Float.MaxValue
  private var max: Float = Float.MinValue
  private var total: Float = 0.0

  @targetName("add")
  def +=(value: Float): Unit = {
    total += value
    min = math.min(min, value)
    max = math.max(max, value)
  }

  @targetName("add")
  def +=(value: MinMaxAvgFloat): Unit = {
    total += value.total
    min = math.min(min, value.min)
    max = math.max(max, value.max)
  }

  @targetName("diff")
  def -=(start: Float, end: Float): Unit = +=(end - start)

  def getMin: Float = min
  def getMax: Float = max
  def getTotal: Float = total
  def getAvg(count: Float): Float = if count == 0.0f then 0.0f else total / count
}
