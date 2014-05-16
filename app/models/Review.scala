package models

import reactivemongo.bson.BSONObjectID
import org.joda.time.DateTime
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.modules.reactivemongo.json.BSONFormats.BSONObjectIDFormat
import scala.Some
import org.joda.time.format.ISODateTimeFormat


case class Review(
  id: Option[BSONObjectID],
  item: BSONObjectID,
  user: BSONObjectID,
  rating: Int,
  text: Option[String],
  created: Option[DateTime],
  updated: Option[DateTime]
)

object Review {

  implicit val jsonReads: Reads[Review] = (
    (__ \ "_id").readNullable[BSONObjectID].map(_.getOrElse(BSONObjectID.generate)).map(Some(_)) and
    (__ \ "item").read[BSONObjectID] and
    (__ \ "user").read[BSONObjectID] and
    (__ \ "rating").read[Int] and
    (__ \ "text").readNullable[String] and
    (__ \ "created").readNullable[DateTime].map(_.getOrElse(new DateTime())).map(Some(_)) and
    (__ \ "updated").readNullable[DateTime].map(_.getOrElse(new DateTime())).map(Some(_))
  )(Review.apply _)

  implicit val jsonWrites: Writes[Review] = (
    (__ \ "_id").writeNullable[BSONObjectID] and
    (__ \ "item").write[BSONObjectID] and
    (__ \ "user").write[BSONObjectID] and
    (__ \ "rating").write[Int] and
    (__ \ "text").writeNullable[String] and
    (__ \ "created").writeNullable[DateTime] and
    (__ \ "updated").writeNullable[DateTime]
  )(unlift(Review.unapply))

}

