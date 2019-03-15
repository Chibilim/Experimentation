package com.ankama.cube.test.rest.Impl

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.server.Route
import com.ankama.cube.test.rest.api.DevelopersApiService
import com.ankama.cube.test.rest.model.InventoryItem
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout
import akka.pattern.ask
import com.ankama.cube.test.inventory.InventoryProtocol.{GetInventoryListRequest, Ok, Result}

import scala.concurrent.ExecutionContextExecutor

class DevelopersService(inventoryActorRef: ActorRef)(implicit system: ActorSystem) extends DevelopersApiService {
  implicit def executionContext: ExecutionContextExecutor = system.dispatcher
  /**
    * Code: 200, Message: search results matching criteria, DataType: List[InventoryItem]
    * Code: 400, Message: bad input parameter
    */
  override def searchInventory(searchString: Option[String], skip: Option[Int], limit: Option[Int])(implicit toEntityMarshallerList: ToEntityMarshaller[List[InventoryItem]]): Route = {
    implicit def requestTimeout: Timeout = Timeout(3, TimeUnit.SECONDS)

    onSuccess(inventoryActorRef.ask(GetInventoryListRequest(searchString, skip, limit)).mapTo[Result[Seq[InventoryItem]]])
    {
      case Ok(seq) => searchInventory200(seq.toList)
      case _       => searchInventory400
    }
  }
}
