package models

import reactivemongo.bson._
import org.joda.time.DateTime
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.modules.reactivemongo.json.BSONFormats.BSONObjectIDFormat
import scala.Some

case class User(
  id: Option[BSONObjectID],
  firstName: String,
  lastName: String,
  email: String,
  username: String,
  created: Option[DateTime],
  updated: Option[DateTime]
)

object User {

  implicit val jsonReads: Reads[User] = (
    (__ \ "_id").readNullable[BSONObjectID].map(_.getOrElse(BSONObjectID.generate)).map(Some(_)) and
    (__ \ "firstName").read[String] and
    (__ \ "lastName").read[String] and
    (__ \ "email").read[String] and
    (__ \ "username").read[String] and
    (__ \ "created").readNullable[DateTime].map(_.getOrElse(new DateTime())).map(Some(_)) and
    (__ \ "updated").readNullable[DateTime].map(_.getOrElse(new DateTime())).map(Some(_))
  )(User.apply _)

  implicit val jsonWrites: Writes[User] = (
    (__ \ "_id").writeNullable[BSONObjectID] and
    (__ \ "firstName").write[String] and
    (__ \ "lastName").write[String] and
    (__ \ "email").write[String] and
    (__ \ "username").write[String] and
    (__ \ "created").writeNullable[DateTime] and
    (__ \ "updated").writeNullable[DateTime]
  )(unlift(User.unapply))

}