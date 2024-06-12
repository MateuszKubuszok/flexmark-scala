package com.kubuszok.scalamd.util

final class DelimitedBuilder(private var delimiter: String, out: StringBuilder) {
  private var pending: Boolean = false
  private var lastLen: Int = 0
  private var delimiterStack: List[String] = Nil

  def this(delimiter: String, capacity: Int) = this(delimiter, new StringBuilder(capacity))
  def this(delimiter: String) = this(delimiter, 0)
  def this() = this(",", 0)

  override def toString: String =
    if delimiterStack != Nil then { throw new IllegalStateException("Delimiter stack is not empty") }
    else out.toString

  def isEmpty: Boolean = return !(pending) && (out == null || out.isEmpty)

  def getAndClear(): String = {
    if delimiterStack != Nil then { throw new IllegalStateException("Delimiter stack is not empty") }
    val result: String = out.toString
    clear()
    result
  }

  def clear(): DelimitedBuilder = {
    out.clear()
    unmark()
    this
  }

  def toStringOrNull: String = {
    if delimiterStack != Nil then { throw new IllegalStateException("Delimiter stack is not empty") }
    out.toString
  }

  def mark(): DelimitedBuilder = {
    val length: Int = out.length
    if lastLen != length then { pending = true }
    lastLen = length
    this
  }

  def unmark(): DelimitedBuilder = {
    pending = false
    lastLen = out.length
    this
  }

  def push(): DelimitedBuilder = return push(delimiter)

  def push(delimiter: String): DelimitedBuilder = {
    unmark()
    delimiterStack = this.delimiter :: delimiterStack
    this.delimiter = delimiter
    this
  }

  def pop(): DelimitedBuilder = delimiterStack match {
    case newDelimiter :: newDelimiterStack =>
      delimiter = newDelimiter
      delimiterStack = newDelimiterStack
      this
    case Nil =>
      throw new IllegalStateException("Nothing on the delimiter stack")
  }

  private def doPending(): Unit =
    if pending then {
      out.append(delimiter)
      pending = false
    }

  def append(v: Char): DelimitedBuilder = {
    doPending()
    out.append(v)
    this
  }

  def append(v: Int): DelimitedBuilder = {
    doPending()
    out.append(v)
    this
  }

  def append(v: Boolean): DelimitedBuilder = {
    doPending()
    out.append(v)
    this
  }

  def append(v: Long): DelimitedBuilder = {
    doPending()
    out.append(v)
    this
  }

  def append(v: Float): DelimitedBuilder = {
    doPending()
    out.append(v)
    this
  }

  def append(v: Double): DelimitedBuilder = {
    doPending()
    out.append(v)
    this
  }

  def append(v: String): DelimitedBuilder = {
    if v != null && v.nonEmpty then {
      doPending()
      out.append(v)
    }
    this
  }

  def append(v: String, start: Int, end: Int): DelimitedBuilder = {
    if v != null && start < end then {
      doPending()
      out.append(v, start, end)
    }
    this
  }

  def append(v: CharSequence): DelimitedBuilder = {
    if v != null && v.length > 0 then {
      doPending()
      out.append(v)
    }
    this
  }

  def append(v: CharSequence, start: Int, end: Int): DelimitedBuilder = {
    if v != null && start < end then {
      doPending()
      out.append(v, start, end)
    }
    this
  }

  def append(v: Array[Char]): DelimitedBuilder = {
    if v.length > 0 then {
      doPending()
      out.append(v)
    }
    this
  }

  def append(v: Array[Char], start: Int, end: Int): DelimitedBuilder = {
    if start < end then {
      doPending()
      out.append(v, start, end)
    }
    this
  }

  def append(o: AnyRef): DelimitedBuilder = return append(o.toString)

  def appendCodePoint(codePoint: Int): DelimitedBuilder = {
    doPending()
    out.underlying.appendCodePoint(codePoint)
    this
  }

  def appendAll[V](v: Array[V]): DelimitedBuilder = return appendAll(v, 0, v.length)

  def appendAll[V](v: Array[V], start: Int, end: Int): DelimitedBuilder = {
    for i <- start until end do {
      val item: V = v(i)
      append(item.toString)
      mark()
    }
    this
  }

  def appendAll[V](delimiter: String, v: Array[V]): DelimitedBuilder = return appendAll(delimiter, v, 0, v.length)

  def appendAll[V](delimiter: String, v: Array[V], start: Int, end: Int): DelimitedBuilder = {
    val lastLength: Int = out.length
    push(delimiter)
    appendAll(v, start, end)
    pop()
    if lastLength != out.length then mark() else unmark()
    this
  }

  def appendAll[V](v: List[? <: V]): DelimitedBuilder = return appendAll(v, 0, v.size)

  def appendAll[V](v: List[? <: V], start: Int, end: Int): DelimitedBuilder = {
    for item <- v do {
      append(item.toString)
      mark()
    }
    this
  }

  def appendAll[V](delimiter: String, v: List[? <: V]): DelimitedBuilder = appendAll(delimiter, v, 0, v.size)

  def appendAll[V](delimiter: String, v: List[? <: V], start: Int, end: Int): DelimitedBuilder = {
    val lastLength: Int = out.length
    push(delimiter)
    appendAll(v, start, end)
    pop()
    if lastLength != out.length then mark() else unmark()
    this
  }
}
