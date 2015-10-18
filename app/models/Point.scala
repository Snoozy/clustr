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

    def byCoordinates(latitude: Double, longitude: Double, offset: Int) = {
        DB.withConnection { implicit connection =>
            SQL("SELECT * FROM point WHERE (latitude > {lat} - {offset} AND latitude < {lat} + {offset}) AND (longitude > {long} - {offset} AMD longitude < {long} + {offset})")
                .on('lat -> latitude, 'long -> longitude, 'offset -> offset).as(pointParser *)
        }
    }

}