import scala.collection.mutable

/**
  * Created by tomcat on 3/13/16.
  *
  * The state begins as a superposition of all possible states;
  * every successful exclusion ratchets down the uncertainty
  * until there is only one.
  */
class CellPotentials(n: Int) {
  val numberOfIntrinsicallyPossibleValues = n
  var fixedValue: Option[Int] = None
  val possibleValues = new mutable.HashSet[Int]()
  reset

  def reset: Unit = {
    if (numberOfIntrinsicallyPossibleValues == 1){
      possibleValues += 1
      fixTheValue
    }
    (1 to numberOfIntrinsicallyPossibleValues).foreach(possibleValues += _)
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

