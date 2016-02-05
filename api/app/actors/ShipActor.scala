package actors

import java.util

import akka.actor.{Props, ActorRef, Actor}
import akka.actor.Actor.Receive
import akka.event.Logging
import org.apache.commons.math3.geometry.euclidean.twod.{Vector2D, Vector2DFormat}
import play.api.libs.json.JsValue



class ShipActor(var out:ActorRef, id:String) extends Actor{

  val velocity=10.0
  var x=0.0
  var y=0.0
  val log = Logging(context.system, this)

  def this(out:ActorRef){
    this(out,"randomActor" + Math.random())
  }

  override def receive: Receive = {
    case "start" => log.info(s"Actor $id was started")
    case json:JsValue =>
      log info(s"JsValue was $json")
      val angle = (json \ "angle").get.as[Double]
      val vector = new Vector2D(Math cos(x),Math cos(y))
      vector scalarMultiply(velocity)

      x += vector getX()
      y += vector getY()
    case "whereareyou" => log.info(s"Actor $id is at: ($x,$y)")
    case _ => log.info("Unknown message")
  }
}

object ShipActor {
  def props(out: ActorRef) = Props(new ShipActor(out))
}
