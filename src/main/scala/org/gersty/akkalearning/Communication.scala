package org.gersty.akkalearning

import akka.actor.ActorRef
import akka.actor.Actor
import scala.util.Random._
import akka.actor.ActorSystem
import akka.actor.Props

object Communication extends App {
  import Messages._
  val actorSystem = ActorSystem("HelloAkka")
  val randomNumberGenerator = actorSystem.actorOf(Props[RandomNumberGeneratorActor],"rando")
  val queryActor = actorSystem.actorOf(Props[QueryActor],"query")
  queryActor ! Start(randomNumberGenerator)
}

object Messages {
  case class Done(randomNumber: Int)
  case object GiveMeRandomNumber
  case class Start(actorRef: ActorRef)
}

class RandomNumberGeneratorActor extends Actor{
  import Messages._
  override def receive: Receive = { 
    case GiveMeRandomNumber =>
      println("received a message to generate a random integer")
      val randomNumber = nextInt 
      sender ! Done(randomNumber)
  }
}

class QueryActor extends Actor {
  import Messages._
  override def receive: Receive = {
    case Start(actorRef) => println(s"send me the next random number")
      actorRef ! GiveMeRandomNumber
    case Done(randomNumber) =>
      println(s"received a random number $randomNumber")
  }
}