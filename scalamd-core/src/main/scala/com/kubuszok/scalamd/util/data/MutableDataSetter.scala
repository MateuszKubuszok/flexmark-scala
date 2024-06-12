package com.kubuszok.scalamd.util.data

trait MutableDataSetter {
  
  def setIn(dataHolder: MutableDataHolder): MutableDataHolder
}
