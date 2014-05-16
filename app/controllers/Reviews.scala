package controllers

import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.BSONFormats.BSONObjectIDFormat
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import scala.concurrent.Future
import org.joda.time.DateTime
import play.api.Logger


object Reviews extends Controller with MongoController {

  def collection: JSONCollection = db.collection[JSONCollection]("reviews")

  import models.Review

  def findByItemId(itemId: String) = Action.async { implicit request =>
    val cur = collection.find(Json.obj("item" -> Json.obj("$oid" -> itemId))).cursor[Review]

    cur.collect[List]().map { reviews =>
      Ok(Json.obj(
        "msg" -> ("Found " + reviews.length + " reviews"),
        "count" -> reviews.length,
        "reviews" -> Json.toJson(reviews)))
    }.recover {
      case e => BadRequest(Json.obj("msg" -> e.getMessage()))
    }
  }

  def create(itemId: String) = Action.async(parse.json) { implicit request =>
    request.body.validate[Review].map { review =>
      collection.insert(review).map { _ =>
        Ok(Json.obj(
          "msg" -> "Review created",
          "review" -> Json.toJson(review)))
      }
    }.getOrElse(Future.successful(BadRequest(Json.obj("msg" -> "Invalid json"))))
  }

  def findById(itemId: String, reviewId: String) = Action.async { implicit request =>
    collection.find(Json.obj("_id" -> Json.obj("$oid" -> reviewId))).one[Review].map { review =>
      Ok(Json.obj(
        "msg" -> "Review found",
        "review" -> Json.toJson(review)))
    }.recover {
      case e => BadRequest(Json.obj("msg" -> e.getMessage()))
    }
  }

  def update(itemId: String, reviewId: String) = Action.async(parse.json) { implicit request =>
    request.body.validate[Review].map { review =>
      val objectId = Json.obj("_id" -> Json.obj("$oid" -> reviewId))
      val modifier = Json.obj(
        "$set" -> Json.obj(
          "item" -> review.item,
          "user" -> review.user,
          "rating" -> review.rating,
          "text" -> review.text,
          "updated" -> new DateTime().getMillis))
      collection.update(objectId, modifier).map { _ =>
        Ok(Json.obj(
          "msg" -> "Review updated",
          "review" -> Json.toJson(review)))
      }
    }.getOrElse(Future.successful(BadRequest(Json.obj("msg" -> "Invalid json"))))
  }

  def delete(itemId: String, reviewId: String) = Action.async { implicit request =>
    collection.remove(Json.obj("_id" -> Json.obj("$oid" -> reviewId))).map { lastError =>
      Ok(Json.obj("msg" -> "Review deleted"))
    }.recover {
      case e => BadRequest(Json.obj("msg" -> e.getMessage()))
    }
  }

  def findByUserId(userId: String) = Action.async { implicit request =>
    collection.find(Json.obj("user" -> Json.obj("$oid" -> userId))).one[Review].map { review =>
      Ok(Json.obj(
        "msg" -> "Review found",
        "review" -> Json.toJson(review)))
    }.recover {
      case e => BadRequest(Json.obj("msg" -> e.getMessage()))
    }
  }

}
