package com.ankama.cube.test.inventory

import akka.actor.{Actor, Props}
import com.ankama.cube.test.inventory.InventoryProtocol.{AddInventoryItemRequest, GetInventoryListRequest, ItemAlreadyExist, Ok, Result}
import com.ankama.cube.test.rest.model.InventoryItem

class InventoryService extends Actor {

  override def receive: Receive = receiveWith(Seq.empty)

  def receiveWith(inv : Seq[InventoryItem]): Receive = {
    case AddInventoryItemRequest(item : InventoryItem)=>
      {
        if (inv.exists(_.id == item.id)) {
          sender() ! ItemAlreadyExist()
        } else {
          context become receiveWith(inv :+ item)
          sender() ! Ok(true)
        }
      }
    case GetInventoryListRequest(searchWord : Option[String], skip: Option[Int], limit: Option[Int])=>
      {
        sender() ! GetItems(inv, searchWord, skip, limit)
      }
  }

  def GetItems(inv:Seq[InventoryItem], searchString: Option[String], skip: Option[Int], limit: Option[Int]): Result[Seq[InventoryItem]] = {
    var items = inv
    items = searchString match {
      case Some(s) => items.filter(_.name.contains(s))
      case None    => items
    }

    items = skip match {
      case Some(c) => items.drop(c)
      case None    => items
    }

    items = limit match {
      case Some(c) => items.take(c)
      case None    => items
    }

    Ok(items)
  }
}

object InventoryService{
  def props = Props(new InventoryService)
}
