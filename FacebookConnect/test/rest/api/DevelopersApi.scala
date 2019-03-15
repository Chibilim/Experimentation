package com.ankama.cube.test.rest.api

import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.ankama.cube.test.rest.model.InventoryItem

class DevelopersApi(
                     developersService: DevelopersApiService,
                     developersMarshaller: DevelopersApiMarshaller
                   ) {

  import developersMarshaller._

  lazy val route: Route =
    path("inventory") {
      get {
        parameters("searchString".as[String].?, "skip".as[Int].?, "limit".as[Int].?) { (searchString, skip, limit) =>
          developersService.searchInventory(searchString = searchString, skip = skip, limit = limit)

        }
      }
    }
}

trait DevelopersApiService {

  def searchInventory200(responseList: List[InventoryItem])(implicit toEntityMarshallerList: ToEntityMarshaller[List[InventoryItem]]): Route =
    complete((200, responseList))

  def searchInventory400: Route =
    complete((400, "bad input parameter"))

  /**
    * Code: 200, Message: search results matching criteria, DataType: List[InventoryItem]
    * Code: 400, Message: bad input parameter
    */
  def searchInventory(searchString: Option[String], skip: Option[Int], limit: Option[Int])
                     (implicit toEntityMarshallerList: ToEntityMarshaller[List[InventoryItem]]): Route

}

trait DevelopersApiMarshaller {

  implicit def toEntityMarshallerList: ToEntityMarshaller[List[InventoryItem]]

}

