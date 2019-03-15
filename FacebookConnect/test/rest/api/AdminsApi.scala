package com.ankama.cube.test.rest.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.Credentials
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import com.ankama.cube.test.rest.model.InventoryItem

class AdminsApi(
                 adminsService: AdminsApiService,
                 adminsMarshaller: AdminsApiMarshaller
               ) {

  import adminsMarshaller._

  def myUserPassAuthenticator(credentials: Credentials): Option[String] =
    credentials match {
      case p @ Credentials.Provided(id) if p.verify("p4ssw0rd") && p.identifier=="bob" => Some(id)
      case _ => None
    }

  lazy val route: Route =
    path("inventory") {
      post {
        authenticateOAuth2("Secure Site", myUserPassAuthenticator){login =>
//        authenticateBasic(realm = "Secure Site", myUserPassAuthenticator){ login=>
//        headerValueByName("login") { login =>
//          if (login == "bob") {
            entity(as[InventoryItem]) { body =>
              adminsService.addInventory(body = body)
            }
//          } else
//            complete((401, "unvalid user"))
        }
      }
    }
}

trait AdminsApiService {

  def addInventory201: Route =
    complete((201, "item created"))

  def addInventory400: Route =
    complete((400, "invalid input, object invalid"))

  def addInventory409: Route =
    complete((409, "an existing item already exists"))

  /**
    * Code: 201, Message: item created
    * Code: 400, Message: invalid input, object invalid
    * Code: 409, Message: an existing item already exists
    */
  def addInventory(body: InventoryItem): Route

}

trait AdminsApiMarshaller {
  implicit def fromRequestUnmarshallerInventoryItem: FromEntityUnmarshaller[InventoryItem]


}

