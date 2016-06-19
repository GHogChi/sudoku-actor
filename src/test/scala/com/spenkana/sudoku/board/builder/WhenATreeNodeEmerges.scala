package com.spenkana.sudoku.board.builder

/**
  * A tree node is an anticorruption layer between a network of ordinary
  * objects and an actor framework like Akka. It's generic for both types,
  * supporting messaging in both directions based on mapping actor and network
  * objects. The network is built first, then the actors are layered over it
  * the key to runtime purity is the bimap, which is being populate while the
  * treenode holds a reference to it. Before it is ever used, the builder
  * locks it: it is immutable when the actors are performing.
  * Created by tomcat on 5/22/16.
  */
class WhenATreeNodeEmerges {
//TODO has this newborn been abandoned?
}
