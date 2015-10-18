package controllers

import play.api._
import play.api.mvc._
import models.Point
import play.api.libs.json.{JsValue, Json}

object Application extends Controller {

    def ping = Action { implicit request =>
      val longitude = request.getQueryString("longitude")
      val latitude = request.getQueryString("latitude")
      val deviceToken = request.getQueryString("device_token")
      if (longitude.isDefined && latitude.isDefined && deviceToken.isDefined) {
          Point.create(latitude.get.toDouble, longitude.get.toDouble, deviceToken.get)
          Ok(Json.obj("status" -> "success"))
      } else {
          BadRequest("status" -> "error")
      }
    }

    private val widthFactor = 100
    private val heightFactor = 100

    def getMap = Action { implicit request =>
        val cornerTLX = request.getQueryString("corner_tl_x")
        val cornerTLY = request.getQueryString("corner_tl_y")
        val cornerBRX = request.getQueryString("corner_br_x")
        val cornerBRY = request.getQueryString("corner_br_y")
        Ok("")
    }

}