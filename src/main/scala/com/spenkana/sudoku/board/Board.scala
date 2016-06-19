package com.spenkana.sudoku.board

import akka.actor.ActorRef

/**
  * Created by tomcat on 3/28/16.
  */
class Board(rowList: List[ActorRef]) {
  val rows = rowList

}
