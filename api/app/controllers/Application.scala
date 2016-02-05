package controllers

import actors.ShipActor
import akka.actor.{Props, ActorSystem}
import model.Movement
import play.api._
import play.api.mvc._

class Application extends Controller {

  val system = ActorSystem("mySystem")

  def createActor = Action { request =>
    request.body.asJson.map(json =>(json \ "id").asOpt[String]).map(stringAsOption =>
      stringAsOption.map(shipId=>
        system.actorOf(Props(new ShipActor(null,shipId)), shipId) ! "start"
      )
    )
    Ok(views.html.index("Your new application is ready."))
  }

  def whereAreYou(shipId:String) = Action {
    val actorRef = system.actorSelection(s"user/$shipId")
    actorRef ! "whereareyou"
    Ok("")
  }

  def move(shipId:String) = Action { request =>
    request.body.asJson.map(bodyAsJson => {
        val actorRef = system.actorSelection(s"user/$shipId")
        actorRef ! bodyAsJson
      }
    )
    Ok("")
  }



}
