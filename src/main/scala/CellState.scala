import scala.collection.mutable

/**
  * Created by tomcat on 3/13/16.
  *
  * The state begins as a superposition of all possible states;
  * every successful exclusion ratchets down the uncertainty
  * until there is only one.
  */
class CellState(n: Int) {
  val intrinsicallyPossibleValues = n
  var fixedValue: Option[Int] = None
  val possibleValues = new mutable.HashSet[Int]()
  reset

  def reset: Unit = {
    (1 to intrinsicallyPossibleValues).foreach(possibleValues += _)
  }

  def exclude(i: Int) = {
    if ((fixedValue == None) && (possibleValues remove i) && (possibleValues size) == 1) {
      fixTheValue
    }
  }

  def fixTheValue: Unit = {
    fixedValue = Some(possibleValues head)
  }

  def currentlyPossibleValues(): Int = {
    possibleValues size
  }
}

