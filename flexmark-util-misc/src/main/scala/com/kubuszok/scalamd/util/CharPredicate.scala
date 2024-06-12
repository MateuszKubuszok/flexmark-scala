package com.kubuszok.scalamd.util

import scala.collection.immutable.BitSet
import scala.util.chaining.*

// CharPredicate.java rewritten

import CharPredicate.*

opaque type CharPredicate = Char => Boolean
object CharPredicate {
  val standard: Map[Char, CharPredicate] = (0 to 127).map(_.toChar).map(char => char -> ((value: Char) => value == char)).toMap

  def standardOrAnyOf(cs: Char*): CharPredicate =
    if cs.isEmpty then NONE
    else if cs.sizeIs == 1 then standard.getOrElse[CharPredicate](cs.head, _ == cs.head)
    else BitSet.fromSpecific(cs.map(_.toInt)).pipe(chars => value => chars(value.toChar))

  val NONE: CharPredicate = (value: Char) => false
  val ALL: CharPredicate = (value: Char) => true
  val SPACE: CharPredicate = standard(' ')
  val TAB: CharPredicate = standard('\t')
  val EOL: CharPredicate = standard('\n')
  val ANY_EOL: CharPredicate = (value: Char) => value == '\n' || value == '\r'
  val ANY_EOL_NUL: CharPredicate = (value: Char) => value == '\n' || value == '\r' || value == '\u0000'
  val BACKSLASH: CharPredicate = standard('\\')
  val SLASH: CharPredicate = standard('/')
  val LINE_SEP: CharPredicate = (value: Char) => value == '\u2028'
  val HASH: CharPredicate = standard('#')
  val SPACE_TAB: CharPredicate = (value: Char) => value == ' ' || value == '\t'
  val SPACE_TAB_NUL: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\u0000'
  val SPACE_TAB_LINE_SEP: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\u2028'
  val SPACE_TAB_NBSP_LINE_SEP: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\u00A0' || value == '\u2028'
  val SPACE_EOL: CharPredicate = (value: Char) => value == ' ' || value == '\n'
  val SPACE_ANY_EOL: CharPredicate = (value: Char) => value == ' ' || value == '\r' || value == '\n'
  val SPACE_TAB_NBSP: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\u00A0'
  val SPACE_TAB_EOL: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\n'
  val SPACE_TAB_NBSP_EOL: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\n' || value == '\u00A0'
  val WHITESPACE: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\n' || value == '\r'
  val WHITESPACE_OR_NUL: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\n' || value == '\r' || value == '\u0000'
  val WHITESPACE_NBSP: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\n' || value == '\r' || value == '\u00A0'
  val WHITESPACE_NBSP_OR_NUL: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\n' || value == '\r' || value == '\u00A0' || value == '\u0000'
  val BLANKSPACE: CharPredicate = (value: Char) => value == ' ' || value == '\t' || value == '\n' || value == '\r' || value == '\u000B' || value == '\f'
  val HEXADECIMAL_DIGITS: CharPredicate = (value: Char) => value >= '0' && value <= '9' || value >= 'a' && value <= 'f' || value >= 'A' && value <= 'F'
  val DECIMAL_DIGITS: CharPredicate = (value: Char) => value >= '0' && value <= '9'
  val OCTAL_DIGITS: CharPredicate = (value: Char) => value >= '0' && value <= '7'
  val BINARY_DIGITS: CharPredicate = (value: Char) => value >= '0' && value <= '1'
}

extension (predicate: CharPredicate)
  def test(char: Char): Boolean = predicate(char)

  def and(another: CharPredicate): CharPredicate =
    if predicate == another then predicate
    else if predicate == NONE || another == NONE then NONE
    else if predicate == ALL then another
    else if another == ALL then predicate
    else value => predicate(value) && another(value)

  def or(another: CharPredicate): CharPredicate =
    if predicate == another then predicate
    else if predicate == ALL || another == ALL then ALL
    else if predicate == NONE then another
    else if another == NONE then predicate
    else value => predicate(value) || another(value)

  def neg: CharPredicate =
    if predicate == NONE then ALL
    else if predicate == ALL then NONE
    else value => !predicate(value)
