import org.scalatest.FunSpec

/**
  * Created by tomcat on 3/11/16.
  */
class WhenCellStateChanges extends FunSpec {

  describe("due to exclusion"){
    describe("and more than two states are possible") {
      it("state count is lowered by 1") {
        val cellState = new CellState(4)
        assert(cellState.intrinsicallyPossibleValues == 4)
        assert(cellState.currentlyPossibleValues == 4)

        cellState.exclude(1)
        assert(cellState.intrinsicallyPossibleValues == 4)
        assert(cellState.currentlyPossibleValues == 3)
      }
      it("state is not fixed"){
        val cellState = new CellState(4)

        cellState.exclude(3)

        assert(cellState.fixedValue == None)
      }
    }
    describe("and only two states are currently possible"){
      it("state is fixed after an exclusion"){
        val cellState = new CellState(2)
        cellState.exclude(1)

        assert(cellState.fixedValue == Some(2))
      }
    }
    describe("and one value is possible"){
      describe("fixed value is set"){
        val cellState = new CellState(1)

        assert(cellState.fixedValue == Some(1))
      }
      //TODO consider other responses
      describe("exclude is silently ignored"){
        val cellState = new CellState(1)
        assert(cellState.fixedValue == Some(1))

        cellState.exclude(1)
      }
    }
  }
}
