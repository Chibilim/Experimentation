package com.ankama.cube.test

import akka.actor.ActorSystem
import akka.event.slf4j.SLF4JLogging
import com.ankama.cube.test.inventory.InventoryService

object Main extends App with SLF4JLogging{

  System.setProperty("logback.configurationFile", "logback.xml")
  log info s"Start Test"

  private implicit val system = ActorSystem()

  val inventoryServiceRef = system.actorOf(InventoryService.props)
  val restServiceRef = system.actorOf(RestService.props(inventoryServiceRef))
}
