package com.ankama.cube.test.rest.Impl

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.ankama.cube.test.inventory.InventoryProtocol.{AddInventoryItemRequest, ItemAlreadyExist, Ok, Result}
import com.ankama.cube.test.rest.api.AdminsApiService
import com.ankama.cube.test.rest.model.InventoryItem
import akka.http.scaladsl.server.Directives._

import scala.concurrent.{ExecutionContextExecutor, Future}


class AdminsService(inventoryActorRef: ActorRef)(implicit system: ActorSystem) extends AdminsApiService {
  implicit def executionContext: ExecutionContextExecutor = system.dispatcher

  /**
    * Code: 201, Message: item created
    * Code: 400, Message: invalid input, object invalid
    * Code: 409, Message: an existing item already exists
    */
  //  override def addInventory(body: InventoryItem): Route = Inventory.AddItem(body) match {
  //    case Ok(_) => addInventory201
  //    case ItemAlreadyExist => addInventory409
  //    case UnknownError => addInventory400
  //  }

  override def addInventory(body: InventoryItem): Route = {
    implicit def requestTimeout: Timeout = Timeout(3, TimeUnit.SECONDS)

    val f: Future[Result[Any]] = inventoryActorRef.ask(AddInventoryItemRequest(body)).mapTo[Result[Any]]
    onSuccess(f){
      case Ok(_)               => addInventory201
      case _: ItemAlreadyExist => addInventory409
      case _                   => addInventory400
    }
  }
}
