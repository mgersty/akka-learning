package org.gersty.akkalearning

import akka.actor.ActorSystem

object HelloAkkaActorSystem {
  
  def main(args: Array[String]): Unit ={
    val actorSystem = ActorSystem("HelloAkka")
    println(actorSystem)    
  }
  
}