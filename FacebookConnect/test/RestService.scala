package com.ankama.cube.test

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.event.slf4j.SLF4JLogging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.ankama.cube.test.rest.Impl.{AdminsService, DevelopersService, Marshaller}
import com.ankama.cube.test.rest.api.{AdminsApi, DevelopersApi}


class RestService(inventoryService : ActorRef)(implicit system : ActorSystem) extends Actor with SLF4JLogging  {

  log info s"RestService start"

  val config = system.settings.config

  private implicit val materializer: ActorMaterializer = ActorMaterializer()

  val marshaller = new Marshaller()

  import akka.http.scaladsl.server.Directives._
  val routes : Route = new AdminsApi(new AdminsService(inventoryService),marshaller ).route ~ new DevelopersApi(new DevelopersService(inventoryService),marshaller).route

  val bindingFuture = Http().bindAndHandle(routes, "0.0.0.0", 9000)

  override def receive: Receive = {
    case _ =>
  }
}

object RestService{
  def props(inventoryService : ActorRef)(implicit system : ActorSystem) = Props(new RestService(inventoryService))
}
