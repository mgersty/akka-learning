package org.gersty.akkahttplearning.backpressure

import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.util.Random
import scala.io.StdIn
import akka.http.scaladsl.marshalling.ToResponseMarshallable.apply
import akka.http.scaladsl.server.Directive.addByNameNullaryApply
import akka.http.scaladsl.server.RouteResult.route2HandlerFlow

object WebServerBackPressure {

  def main(args: Array[String]) {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    
    val numbers = Source.fromIterator(() => 
        Iterator.continually(Random.nextInt()))
    
    val route = 
      path("random") {
        get{
          complete(
                HttpEntity(
                    ContentTypes.`text/plain(UTF-8)`,
                    numbers.map(n=> ByteString(s"$n\n"))
                )
           )
        }
    }
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}