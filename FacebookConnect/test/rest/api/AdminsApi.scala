package com.ankama.cube.test.rest.api

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.Credentials
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import com.ankama.cube.test.rest.model.InventoryItem
import akka.pattern.ask

import akka.util.Timeout
import com.ankama.cube.test.auth.AuthProtocol.{CredentialRequest, CredentialResult}

import scala.concurrent.{ExecutionContextExecutor, Future}

class AdminsApi(
                 adminsService: AdminsApiService,
                 adminsMarshaller: AdminsApiMarshaller,
                 authService : ActorRef
               )(implicit actorSystem : ActorSystem) {

  import adminsMarshaller._

  implicit def requestTimeout: Timeout = Timeout(3, TimeUnit.SECONDS)
  implicit def executionContext: ExecutionContextExecutor = actorSystem.dispatcher

//  def myUserPassAuthenticator(credentials: Credentials): Option[String] = {
//    credentials match {
//      case p@Credentials.Provided(id) if p.verify("p4ssw0rd") && p.identifier == "bob" => Some(id)
//      case _                                                                           => None
//    }
//  }

  def myUserPassAuthenticator(credentials: Credentials) : Future[Option[String]] =
  {
    credentials match {
      case p@Credentials.Provided(id) =>
        authService.ask(CredentialRequest(id)).mapTo[CredentialResult].map{
          case r : CredentialResult if p.verify(r.secret) => Some(id)
          case _ => None
        }
      case _  => Future.successful(None)
    }
  }

  lazy val route: Route =
    path("inventory") {
      post {
//        authenticateOAuth2("Secure Site", myUserPassAuthenticator){login =>
        authenticateBasicAsync(realm = "Secure Site", myUserPassAuthenticator){ login=>
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

