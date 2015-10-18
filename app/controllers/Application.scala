package controllers

import play.api._
import play.api.mvc._
import models.Point
import play.api.libs.json.{JsValue, Json}

object Application extends Controller {

    private val WidthFactor = 100
    private val HeightFactor = 100

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

    def getMap = Action { implicit request =>
        val cornerTLLat = request.getQueryString("corner_tl_lat")
        val cornerTLLong = request.getQueryString("corner_tl_long")
        val cornerBRLat = request.getQueryString("corner_br_lat")
        val cornerBRLong = request.getQueryString("corner_br_long")
        if (cornerTLLat.isDefined && cornerTLLong.isDefined && cornerBRLat.isDefined && cornerBRLat.isDefined) {
            val points = Point.byCoordinates(cornerTLLat.get.toDouble, cornerTLLong.get.toDouble, cornerBRLat.get.toDouble, cornerBRLong.get.toDouble)
            Ok("")
        } else {
            BadRequest(Json.obj("error" -> "Format incorrect."))
        }
    }

}