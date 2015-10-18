package models

import anorm.SqlParser._
import anorm._
import play.api.Play
import play.api.Play.current
import play.api.db._
import play.api.libs.json._

case class Point (
    latitude: Double,
    longitude: Double,
    deviceToken: String
)
