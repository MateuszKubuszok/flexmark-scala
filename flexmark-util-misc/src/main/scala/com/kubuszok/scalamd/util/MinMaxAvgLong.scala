package com.kubuszok.scalamd.util

import scala.annotation.targetName

class MinMaxAvgLong {
  private var min: Long = Long.MaxValue
  private var max: Long = Long.MinValue
  private var total: Long = 0L

  @targetName("add")
  def +=(value: Long): Unit = {
    total += value
    min = math.min(min, value)
    max = math.max(max, value)
  }

  @targetName("add")
  def +=(value: MinMaxAvgLong): Unit = {
    total += value.total
    min = math.min(min, value.min)
    max = math.max(max, value.max)
  }

  @targetName("diff")
  def -=(start: Long, end: Long): Unit = +=(end - start)

  def getMin: Long = min
  def getMax: Long = max
  def getTotal: Long = total
  def getAvg(count: Long): Long = if count == 0L then 0L else total / count
}
