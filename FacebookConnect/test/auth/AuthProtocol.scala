package com.ankama.cube.test.auth

object AuthProtocol {

  final case class CredentialRequest(login : String)
  final case class CredentialResult(secret : String)

}
