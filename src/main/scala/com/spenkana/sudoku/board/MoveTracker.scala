package com.spenkana.sudoku.board

/**
  * Created by tomcat on 3/21/16.
  */
class MoveTracker(
                   completionNotifier: () => Unit,
                   syncFailureNotifier: () => Unit
                 ) {
  var activePathCount = 0

  def pathsStarted(n: Int): Unit = {
    activePathCount += n
  }

  /**
    * Getting messages out of order only becomes a problem if it
    * forces premature termination. This minimalist implementation
    * can only catch one case: a single path start is delayed and
    * the path end is the first message received, driving the count negative
    * before it has ever been positive.
    */
  def pathEnded: Unit = {
    if (activePathCount == 0) {
      syncFailureNotifier.apply()
      return
    }
    activePathCount -= 1
    if (activePathCount == 0) {
      completionNotifier.apply()
    }
  }
}
