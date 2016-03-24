package com.spenkana.sudoku.actor

import org.scalatest.FunSpec

import scala.collection.mutable

/**
  * Created by tomcat on 3/11/16.
  */
class WhenCellPotentialsChange extends FunSpec {

  def makeAllValues(n: Int): scala.collection.mutable.BitSet = {
    var allValues = new mutable.BitSet()
    for (value <- 1 to n){
      allValues += value
    }
    allValues
  }

  describe("due to exclusion"){
    describe("and more than two states are possible") {
      it("state count is lowered by 1") {
        val cellState = new CellPotentials(makeAllValues(4))
        assert(cellState.numberOfIntrinsicallyPossibleValues == 4)
        assert(cellState.numberOfCurrentlyPossibleValues == 4)

        cellState.exclude(1)
        assert(cellState.numberOfIntrinsicallyPossibleValues == 4)
        assert(cellState.numberOfCurrentlyPossibleValues == 3)
      }
      it("state is not fixed"){
        val cellState = new CellPotentials(makeAllValues(4))

        cellState.exclude(3)

        assert(cellState.fixedValue == None)
      }
    }
    describe("and only two states are currently possible"){
      it("state is fixed after an exclusion"){
        val cellState = new CellPotentials(makeAllValues(2))

        cellState.exclude(1)

        assert(cellState.fixedValue == Some(2))
      }
    }
    describe("and one value is possible"){
      val cellState = new CellPotentials(makeAllValues(1))
      it("fixed value is set"){

        assert(cellState.fixedValue == Some(1))
      }
      //TODO consider other responses
      it("exclude is silently ignored"){

        cellState.exclude(1)

        assert(cellState.fixedValue == Some(1))
      }
    }
  }
}
