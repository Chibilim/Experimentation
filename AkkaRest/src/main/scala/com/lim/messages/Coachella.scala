package com.lim.messages

import akka.actor.Props
import com.lim.actors.Coachella

object Coachella {

  def props : Props = Props(new Coachella)

  final case class CreateEvent(name: String, tickets: Int) // message to create an event
  final case class GetEvent(name: String) // message to get an event
  final case object GetEvents // message to request all events
  final case class GetTickets(event: String, tickets: Int) // message to get tickets for an event
  final case class CancelEvent(name: String) // message to cancel an event

  final case class Event(name: String, tickets: Int) // message describing the event
  final case class Events(events: Vector[Event]) // message describing a list of events

  sealed trait EventResponse // message response to create an event
  final case class EventCreated(event: Event) extends EventResponse // message to indicate the event was created
  final case object EventExists extends EventResponse // message to indicate that the event already exists
}