package com.kubuszok.scalamd.util.data

trait DataKeyAggregator {

  def aggregate(combined: DataHolder): DataHolder
  def aggregateActions(combined: DataHolder, other: DataHolder, overrides: DataHolder): DataHolder

  def clean(combined: DataHolder): DataHolder
  
  //def invokeAfterSet(): Option[Set[Class[?]]] // wtf ???, we do not use global mutable crap :P
}
