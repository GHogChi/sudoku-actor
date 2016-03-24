package com.spenkana.sudoku.actor

/**
  * Created by tomcat on 3/13/16.
  *
  * The state begins as a superposition of all possible states;
  * every successful exclusion ratchets down the uncertainty
  * until there is only one.
  *
  */
//TODO find efficient way to pass immutable BitSet to constructor - can't clone to mutable
class CellPotentials(allPossibleValues: scala.collection.mutable.BitSet) {
  val numberOfIntrinsicallyPossibleValues = allPossibleValues.size
  var fixedValue: Option[Int] = None
  val currentlyPossibleValues = new scala.collection.mutable.BitSet()
  reset

  def reset: Unit = {
    if (numberOfIntrinsicallyPossibleValues == 1){
      currentlyPossibleValues += 1
      fixTheValue
    }
    (1 to numberOfIntrinsicallyPossibleValues).foreach(currentlyPossibleValues += _)
  }

  def exclude(i: Int) = {
    if (fixedValue == None){
      val didRemove: Boolean = currentlyPossibleValues.remove(i)
      if (didRemove && (currentlyPossibleValues size) == 1) {
        fixTheValue
      }
    }
  }

  def fixTheValue: Unit = {
    fixedValue = Some(currentlyPossibleValues head)
  }

  def numberOfCurrentlyPossibleValues(): Int = {
    currentlyPossibleValues.size
  }
}

