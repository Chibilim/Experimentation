package com.ankama.cube.test.auth

import akka.actor.{Actor, Props}
import com.ankama.cube.test.auth.AuthProtocol.{CredentialRequest, CredentialResult}

class AuthService extends Actor {
  override def receive: Receive = {
    case CredentialRequest(id) if id=="bob" => sender() ! CredentialResult("p4ssw0rd")

  }
}

object AuthService {
  def props = Props(new AuthService)
}
