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
          BadRequest(Json.obj("status" -> "error"))
        }
    }

    def getMap = Action { implicit request =>
        val cornerTLLat = request.getQueryString("corner_tl_lat")
        val cornerTLLong = request.getQueryString("corner_tl_long")
        val cornerBRLat = request.getQueryString("corner_br_lat")
        val cornerBRLong = request.getQueryString("corner_br_long")
        if (cornerTLLat.isDefined && cornerTLLong.isDefined && cornerBRLat.isDefined && cornerBRLat.isDefined) {
            val points = Point.byCoordinates(cornerTLLat.get.toDouble, cornerTLLong.get.toDouble, cornerBRLat.get.toDouble, cornerBRLong.get.toDouble)
            val quadrants = Compute.compute(new Coordinate(cornerTLLong.get.toDouble, cornerTLLat.get.toDouble), new Coordinate(cornerBRLong.get.toDouble, cornerBRLat.get.toDouble), WidthFactor, HeightFactor, points.toArray)
            var json = Json.arr()
            quadrants.foreach { quads =>
                var jsonArr = Json.arr()
                quads.foreach{ q =>
                    jsonArr = jsonArr.+:(Json.obj(
                        "coordinate" -> Json.obj("latitude" -> q.coordinate.latitude, "longitude" -> q.coordinate.longitude),
                        "users" -> q.users
                    ))
                }
                json = json.+:(jsonArr)
            }
            Ok(json)
        } else {
            BadRequest(Json.obj("error" -> "Format incorrect."))
        }
    }

}