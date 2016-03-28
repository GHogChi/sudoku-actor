package com.spenkana.sudoku.actor

import org.scalatest.FunSpec

/**
  * Created by tomcat on 3/26/16.
  */
class WhenAMessageCascades extends FunSpec {
  describe("from scratch") {
    it("has a name corresponding to its local index") {
      val message = new InitialName(7)

      assert(message.name == "Root.7")
    }
  }
  describe("from an existing message") {
    describe("with provided index") {
      it("receives a new qualified name") {
        val prevName = new InitialName(3)

        val newName = new IndexedInheritedName(prevName, 4)

        assert(newName.name == "Root.3.4")
      }
    }
    describe("with no index provided"){
      it("keeps name of previous message"){
        val prevName = new InitialName(55)

        val newName = new InheritedName(prevName)

        assert(newName.name == prevName.name)
        assert(newName == prevName)
      }
    }
  }
}
