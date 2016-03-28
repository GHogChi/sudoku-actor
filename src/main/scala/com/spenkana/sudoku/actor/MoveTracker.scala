package com.spenkana.sudoku.actor

import scala.collection.mutable

/**
  * Created by tomcat on 3/21/16.
  */
class MoveTracker(completionNotifier: () => Unit) {
  val pathsStarted = new mutable.HashSet[String]()
  val pathsEnded = new mutable.HashSet[String]()

  def startMessagePath(pathName: String): Unit = {
    pathsStarted += pathName
    checkCompletion
  }


  def endMessagePath(pathName: String): Any = {
    pathsEnded += pathName
    checkCompletion
  }

  def checkCompletion: Unit = {
    if (pathsStarted.size == pathsEnded.size && allPathsAccountedFor){
      completionNotifier.apply()
    }
  }

  def allPathsAccountedFor: Boolean = {
    for (path <- pathsStarted) {
      if (!pathsEnded.contains(path)) {
        return false
      }
    }
    true
  }

}
