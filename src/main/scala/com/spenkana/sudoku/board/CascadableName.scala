package com.spenkana.sudoku.board

/**
  * Created by tomcat on 3/26/16.
  */

abstract class CascadableName(value: String) {
  val name = value

  def canEqual(other: Any): Boolean = other.isInstanceOf[CascadableName]

  override def equals(other: Any): Boolean = other match {
    case that: CascadableName =>
      (that canEqual this) &&
        name == that.name
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(name)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

class InheritedName(parent: CascadableName) extends CascadableName(parent.name) { }

class IndexedInheritedName(parent: CascadableName, index: Int)
  extends CascadableName(parent.name + "." + index)

class InitialName(index: Int) extends CascadableName("Root." + index)


