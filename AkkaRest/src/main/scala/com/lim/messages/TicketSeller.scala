package com.lim.messages

import akka.actor.Props
import com.lim.actors.TicketSeller

object TicketSeller {

  def props(event: String) : Props = Props(new TicketSeller(event))

  final case class Add(tickets: Vector[Ticket]) // message to add tickets to the TicketSeller
  final case class Buy(tickets: Int) // message to buy tickets from the TicketSeller
  final case class Ticket(id: Int) // A ticket
  final case class Tickets(event: String,
                     entries: Vector[Ticket] = Vector.empty[Ticket]) // a list of tickets for an event
  final case object GetEvent // a message containing the remaining tickets for an event
  final case object Cancel // a message to cancel the event
}
