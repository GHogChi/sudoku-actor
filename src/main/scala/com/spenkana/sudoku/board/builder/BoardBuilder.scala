package com.spenkana.sudoku.board.builder

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.spenkana.sudoku.board.Board

import scala.collection.mutable.ListBuffer

/**
  * Created by tomcat on 3/28/16.
  */
class BoardBuilder(rootCardinality: Int) {
  val system = ActorSystem("Board")
  val setSize = rootCardinality * rootCardinality

  def build(boardName: String): Board = {
    val boardOwner = system.actorOf(Props[BoardOwner], name = boardName)
    return null;
  }

}

case class BoardBuildRequest(name: String, sideLength: Int) {}

class BoardOwner extends Actor {

  def build(name: String, sideLength: Int, ref: ActorRef): Unit = {
    val rows: ListBuffer[ActorRef] = new ListBuffer[ActorRef]()

    new Board(rows.toList)

  }

  override def receive: Receive = {
    case buildRequest: BoardBuildRequest => build(buildRequest.name,
      buildRequest.sideLength, sender())
  }
}
