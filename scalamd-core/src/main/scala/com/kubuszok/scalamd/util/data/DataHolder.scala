package com.kubuszok.scalamd.util.data
import scala.annotation.targetName

sealed trait DataHolder extends MutableDataSetter {

  def getAll: Map[DataKey[?], Any]
  def getKeys: Seq[DataKey[?]]

  def contains(key: DataKey[?]): Boolean

  @targetName("getRequired")
  final def get[A](key: DataKey.Required[A]): A = key.get(this)
  @targetName("getOptional")
  final def get[A](key: DataKey.Optional[A]): Option[A] = key.get(Some(this))
  @targetName("get")
  final def get[A](key: DataKey[A]): Option[A] = key match {
    case opt: DataKey.Optional[A] => get(opt)
    case req: DataKey.Required[A] => Some(get(req))
  }
  @targetName("getOrElseRequired")
  final def getOrElse[A](key: DataKey[A], factory: DataValueFactory[A]): A = get(key).getOrElse(factory(this))
  @targetName("getOrElseOptional")
  final def getOrElse[A](key: DataKey[A], factory: OptionalDataValueFactory[A]): Option[A] = get(key).orElse(factory(this))

  final def setIn(dataHolder: MutableDataHolder): MutableDataHolder = dataHolder.setAll(this)

  final def aggregate(ourDataSetAggregators: Seq[DataKeyAggregator]): DataHolder =
    ourDataSetAggregators.foldLeft(this: DataHolder) { (holder, aggregator) =>
      aggregator.aggregate(holder)
    }
  final def aggregateActions(ourDataSetAggregators: Seq[DataKeyAggregator], overrides: DataHolder): DataHolder =
    ourDataSetAggregators.foldLeft(MutableDataSet.empty.setAll(this).setAll(overrides): DataHolder) { (combined, aggregator) =>
      aggregator.aggregateActions(combined, this, overrides)
    }

  // MutableDataSet covers all

  final def toImmutable: DataHolder = toDataSet
  final def toMutable: MutableDataHolder = this match {
    case mds: MutableDataSet => mds
  }

  final def toDataSet: DataSet = this match {
    case mds: MutableDataSet => mds
  }
}
object DataHolder {

  def empty: DataHolder = MutableDataSet.empty
}

sealed trait MutableDataHolder extends DataHolder {

  def set[A](key: DataKey.Optional[A], value: Option[A]): MutableDataHolder
  def set[A](key: DataKey.Required[A], value: A): MutableDataHolder

  def remove[A](key: DataKey[A]): MutableDataHolder

  def setFrom(dataSetter: MutableDataSetter): MutableDataHolder
  def setAll(other: DataHolder): MutableDataHolder

  def clear(): MutableDataHolder
}

sealed trait DataSet extends DataHolder {
  protected var impl: Map[DataKey[?], Any] = Map.empty

  final override def getAll: Map[DataKey[?], Any] = impl
  final override def getKeys: Seq[DataKey[?]] = impl.keys.toSeq

  final override def contains(key: DataKey[?]): Boolean = impl.contains(key)

  final def merge(dataHolders: DataHolder*): DataSet =
    dataHolders.foldLeft(MutableDataSet.empty) { (holder, another) =>
      holder.setAll(another)
    }
}

sealed trait ScopedDataSet(protected val parent: DataHolder) extends DataSet

sealed trait MutableDataSet extends DataSet with MutableDataHolder {

  override def set[A](key: DataKey.Optional[A], value: Option[A]): MutableDataSet = set(key: DataKey[A], value)
  override def set[A](key: DataKey.Required[A], value: A): MutableDataSet = set(key: DataKey[A], Some(value))
  private def set[A](key: DataKey[A], value: Option[A]): MutableDataSet = {
    impl = impl.updatedWith(key)(_ => value)
    this
  }

  override def remove[A](key: DataKey[A]): MutableDataSet = {
    impl = impl.removed(key)
    this
  }

  override def setFrom(dataSetter: MutableDataSetter): MutableDataSet = {
    dataSetter.setIn(this)
    this
  }
  override def setAll(other: DataHolder): MutableDataSet = {
    impl = impl ++ other.getAll
    this
  }

  override def clear(): MutableDataSet = {
    impl = Map.empty
    this
  }
}
object MutableDataSet {

  def empty: MutableDataSet = new MutableUnscopedDataSet
}

final class MutableUnscopedDataSet extends MutableDataSet
final class MutableScopedDataSet(parent: DataHolder) extends ScopedDataSet(parent) with MutableDataSet
