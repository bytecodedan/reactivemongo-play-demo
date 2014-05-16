package models

import reactivemongo.bson._
import org.joda.time.DateTime
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.modules.reactivemongo.json.BSONFormats.BSONObjectIDFormat
import scala.Some
import org.joda.time.format.ISODateTimeFormat

case class Item(
  id: Option[BSONObjectID],
  name: String,
  created: Option[DateTime],
  updated: Option[DateTime]
)

object Item {

  implicit val jsonReads: Reads[Item] = (
    (__ \ "_id").readNullable[BSONObjectID].map(_.getOrElse(BSONObjectID.generate)).map(Some(_)) and
    (__ \ "name").read[String] and
    (__ \ "created").readNullable[DateTime].map(_.getOrElse(new DateTime())).map(Some(_)) and
    (__ \ "updated").readNullable[DateTime].map(_.getOrElse(new DateTime())).map(Some(_))
  )(Item.apply _)

  implicit val jsonWrites: Writes[Item] = (
    (__ \ "_id").writeNullable[BSONObjectID] and
    (__ \ "name").write[String] and
//    (__ \ "reviews").writeNullable[List[Review]] and
    (__ \ "created").writeNullable[DateTime] and
    (__ \ "updated").writeNullable[DateTime]
  )(unlift(Item.unapply))

}