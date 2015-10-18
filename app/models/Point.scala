package models

import anorm.SqlParser._
import anorm._
import play.api.Play
import play.api.Play.current
import play.api.db._
import play.api.libs.json._

case class Point (
    pointId: Int,
    latitude: Double,
    longitude: Double,
    deviceToken: String
)

object Point {

    private val pointParser: RowParser[Point] = {
        get[Option[Int]]("point_id") ~
            get[Double]("latitude") ~
            get[Double]("longitude") ~
            get[String]("device_token") map {
            case pointId ~ latitude ~ longitude ~ deviceToken =>
                Point(pointId.get, latitude, longitude, deviceToken)
        }
    }

    def create(latitude: Double, longitude: Double, deviceToken: String) = {
        DB.withConnection { implicit connection =>
            SQL("INSERT INTO point (latitude, longitude, device_token) VALUES ({lat}, {long}, {device}")
                .on('lat -> latitude, 'long -> longitude, 'device -> deviceToken).executeUpdate()
        }
    }

    def byDeviceToken(deviceToken: String) = {
        DB.withConnection { implicit connection =>
            SQL("SELECT * FROM point WHERE device_token = {device} LIMIT 1")
                .on('device -> deviceToken).as(pointParser.singleOpt)
        }
    }

    def byCoordinates(latitude1: Double, longitude1: Double, latitude2: Double, longitude2: Double) = {
        DB.withConnection { implicit connection =>
            SQL("SELECT * FROM point WHERE (latitude > {lat2} AND latitude < {lat1}) AND (longitude > {long2} AMD longitude < {long1}")
                .on('lat2 -> latitude2, 'long2 -> longitude2, 'long1 -> longitude1, 'lat1 -> latitude1).as(pointParser *)
        }
    }

}