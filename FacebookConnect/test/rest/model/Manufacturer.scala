package com.ankama.cube.test.rest.model


/**
 * @param name  for example: ''ACME Corporation''
 * @param homePage  for example: ''https://www.acme-corp.com''
 * @param phone  for example: ''408-867-5309''
 */
case class Manufacturer (
  name: String,
  homePage: Option[String],
  phone: Option[String]
)

