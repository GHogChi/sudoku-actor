package com.spenkana.sudoku.board

import org.scalamock.scalatest.MockFactory
import org.scalatest.FunSpec

/**
  * Created by tomcat on 3/20/16.
  * A move made by a program or a user is the placing of a value on a cell
  * followed by a complete automated response.
  * The target of this test is a move tracker.
  * Each move triggers a chain reaction:
  * message trajectories branch and terminate.
  *
  * This tracker responds to two messages:
  * A: <n> paths started
  * B: path ended
  *
  * One variable is maintained: apc (active path count)
  * On receipt of A, apc is incremented by n
  * On receipt of B, apc is decremented by 1
  * When apc returns to zero, it's the end of the move.
  *
  * A move starts one or more branches: the target of a move is always
  * a cell, and excluding or fixing a value on a cell will cause it to send
  * three messages (for a 2D board) to the sets it belongs to, followed by
  * message A(3) to the tracker
  * - or,
  * if the message is redundant (excluding
  * an already excluded value; fixing a fixed value),
  * no messages at all to sets, but a B to the tracker.
  *
  * Okay - event source it all. Every user action and every state change kept in
  * a data structure to the end of the game. Undoable.
  */
//TODO figure out why vals can't be defined up front

class WhenAMoveIsTracked extends FunSpec with MockFactory {


  describe("and a single path starts") {
    it("does not trigger completion") {
      val completionNotifier = mockFunction[Unit]
      val syncFailureNotifier = mockFunction[Unit]
      val moveTracker = new MoveTracker(completionNotifier, syncFailureNotifier)
      completionNotifier expects() never()

      moveTracker.pathsStarted(1)
    }
  }

  describe("and a single path starts and ends") {
    it("triggers completion") {
      val completionNotifier = mockFunction[Unit]
      val syncFailureNotifier = mockFunction[Unit]
      val moveTracker = new MoveTracker(completionNotifier, syncFailureNotifier)
      completionNotifier expects() once()

      moveTracker.pathsStarted(1)
      moveTracker.pathEnded
    }
  }

  /**
    * TODO figure out if this should be treated as a failure.
    * Messages out of sync could prematurely terminate a
    * move - i.e., while cells were still communicating.
    * This test only works because these are the first two
    * messages. Out of sync endings later in the move
    * could cause untimely death.  Of the move, of course.
    * Check that the first message is not pathEnded;
    * don't go negative!
    *
    * Okay, yes - going negative is the only case in which
    * the tracker could detect serialization failure. But
    * there's no point in returning anything to the cell
    * that sent the pathEnded message. So we'll report it.
    * Sorry - no other info is available.
    */
  describe("end message received before start") {
    it("completes ok") {
      val completionNotifier = mockFunction[Unit]
      val syncFailureNotifier = mockFunction[Unit]
      val moveTracker = new MoveTracker(completionNotifier, syncFailureNotifier)
      completionNotifier expects() never()
      syncFailureNotifier expects() once()

      moveTracker.pathEnded
      moveTracker.pathsStarted(1)
    }
  }

  describe("out of sync detected") {
    it("reports the incident") {
      val completionNotifier = mockFunction[Unit]
      val syncFailureNotifier = mockFunction[Unit]
      val moveTracker = new MoveTracker(completionNotifier, syncFailureNotifier)
      syncFailureNotifier expects() once()

      moveTracker.pathEnded
    }
  }

}
