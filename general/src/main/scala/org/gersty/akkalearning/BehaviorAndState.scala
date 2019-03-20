package org.gersty.akkalearning

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class BehaviorAndState {
  

 
}
class SummingActorWithConstructor(initialSum: Int) extends Actor {
    var sum = 0
    override def receive: Receive = {
      case x: Int => sum = sum + x
        println(s"my state as sum is $sum")
      case _=> println("I don't know what you are talking about")
    }
}
  

object BehaviorAndState extends App {
  val actorSystem = ActorSystem("HelloAkka")
  val actor = actorSystem.actorOf(Props(classOf[SummingActorWithConstructor], 10),"summingactor")
  println(actor.path)
  while (true) {
    Thread.sleep(3000)
    actor ! "Hello"
  }
  
}