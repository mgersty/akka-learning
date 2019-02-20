package org.gersty.akkalearning

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.{ExecutionContext, Future}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._

object WebServer {

  def main(args: Array[String]){
    val host = "0.0.0.0"
    val port = 9000

    implicit val system: ActorSystem = ActorSystem("helloworld")
    implicit val executor: ExecutionContext = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    def route = path("hello"){
      get {
        complete("Hello, World!")
      }
    }


    Http().bindAndHandle(route, host, port)

  }



}