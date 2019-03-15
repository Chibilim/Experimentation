package com.ankama.cube.test.inventory

import com.ankama.cube.test.rest.model.InventoryItem

object InventoryProtocol {

  sealed trait Result[T]

  final case class Ok[T](value: T) extends Result[T]
  final case class ItemAlreadyExist() extends Result[Any]
  final case class UnknownError() extends Result[Any]


  final case class AddInventoryItemRequest(item : InventoryItem)


  final case class GetInventoryListRequest(searchWord : Option[String], skip: Option[Int], limit: Option[Int])
}
