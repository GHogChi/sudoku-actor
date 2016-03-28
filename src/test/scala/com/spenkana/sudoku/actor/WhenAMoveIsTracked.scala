package com.spenkana.sudoku.actor

import org.scalamock.scalatest.MockFactory
import org.scalatest.FunSpec

/**
  * Created by tomcat on 3/20/16.
  * A move made by a program or a user is the placing of a value on a cell
  * followed by a complete automated response.
  * The target of this test is a move tracker.
  * Each move triggers a chain reaction:
  * message trajectories branch and terminate.
  * This tracker responds to messages consisting of
  * a branch name
  * eventType: birth or death
  *
  * It should receive one birth notice and one death notice for each
  * branch name.
  * Names are like xpaths - but
  * if a message is relayed to one recipient, no name is added to
  * the path: the parent's name is retained. In this case, there
  * would be no message to the tracker: no branch was born or died.
  *
  * A move starts one or more branches: the target of a move is always
  * a cell, and excluding or fixing a value on a cell will cause it to send
  * three messages (for a 2D board) - or, if the message is redundant (excluding
  * an already excluded value; fixing a fixed value), no messages at all. Does
  * this last case require a new message?
  *
  * Okay - event source it all. Every user action and every state change kept in
  * a data structure to the end of the game. Undoable.
  */
//TODO implement a delay based on the mean time btw message receipts - to catch possible stragglers.
class WhenAMoveIsTracked extends FunSpec with MockFactory {
  describe("and a single path starts") {
    it("does not trigger completion") {
      val completionNotifier = mockFunction[Unit]
      completionNotifier expects() never()
      val moveTracker = new MoveTracker(completionNotifier)

      moveTracker.startMessagePath("name")
    }
  }

  describe("and a single path starts and ends") {
    it("triggers completion") {
      val completionNotifier = mockFunction[Unit]
      completionNotifier expects() once()
      val moveTracker = new MoveTracker(completionNotifier)

      moveTracker.startMessagePath("name")
      moveTracker.endMessagePath("name")
    }
  }
  describe("end message received before start") {
    it("completes ok") {
      val completionNotifier = mockFunction[Unit]
      completionNotifier expects() once()
      val moveTracker = new MoveTracker(completionNotifier)

      moveTracker.endMessagePath("name")
      moveTracker.startMessagePath("name")
    }
  }

}
