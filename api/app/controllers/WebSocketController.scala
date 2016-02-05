package controllers

import actors.ShipActor
import play.api.Logger
import play.api.libs.json.JsValue
import play.api.mvc.{WebSocket, Action, Controller}
import play.api.Play.current

/**
 * Created by nfaure on 1/26/16.
 */
class WebSocketController extends Controller {

  def ship = WebSocket.acceptWithActor[JsValue, JsValue] { req => out =>
    Logger.info("Web socket Message received ")
    ShipActor.props(out)
  }

}
