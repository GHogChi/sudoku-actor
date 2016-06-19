package com.spenkana.sudoku.board.builder

import com.spenkana.sudoku.board.Board
import org.scalatest.FunSpec

/**
  * Created by tomcat on 3/28/16.
  */
class WhenBuilderCreatesCellSets extends FunSpec {
  describe("given root cardinality C"){
    val c = 2
    it("creates c**2 rows"){
      val builder = new BoardBuilder(c)

      val board: Board = builder.build("boardName")

      assert(board.rows.size == (c * c))
    }
  }
}
