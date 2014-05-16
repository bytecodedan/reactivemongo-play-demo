package controllers

import play.api.mvc._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import scala.concurrent.Future
import org.joda.time.DateTime

object Items extends Controller with MongoController {

  def collection: JSONCollection = db.collection[JSONCollection]("items")

  import models.Item

  def index = Action.async { implicit request =>
    val col = collection.find(Json.obj()).cursor[Item]

    col.collect[List]().map { items =>
      Ok(Json.obj(
        "msg" -> ("Found " + items.length + " items"),
        "count" -> items.length,
        "items" -> Json.toJson(items)))
    }.recover {
      case e => BadRequest(Json.obj("msg" -> e.getMessage()))
    }
  }

  def create = Action.async(parse.json) { implicit request =>
    request.body.validate[Item].map { item =>
      collection.insert(item).map { _ =>
        Ok(Json.obj(
          "msg" -> "Item created",
          "item" -> Json.toJson(item)))
      }
    }.getOrElse(Future.successful(BadRequest(Json.obj("msg" -> "Invalid json"))))
  }

  def find(id: String) = Action.async { implicit request =>
    collection.find(Json.obj("_id" -> Json.obj("$oid" -> id))).one[Item].map { item =>
      Ok(Json.obj(
        "msg" -> "Item found",
        "item" -> Json.toJson(item)))
    }.recover {
      case e => BadRequest(Json.obj("msg" -> e.getMessage()))
    }
  }

  def update(id: String) = Action.async(parse.json) { implicit request =>
    request.body.validate[Item].map { item =>
      val objectId = Json.obj("_id" -> Json.obj("$oid" -> id))
      val modifier = Json.obj(
        "$set" -> Json.obj(
          "name" -> item.name,
          "updated" -> new DateTime().getMillis))
      collection.update(objectId, modifier).map { _ =>
        Ok(Json.obj(
          "msg" -> "Item updated",
          "item" -> Json.toJson(item)))
      }
    }.getOrElse(Future.successful(BadRequest(Json.obj("msg" -> "Invalid json"))))
  }

  def delete(id: String) = Action.async { implicit request =>
    collection.remove(Json.obj("_id" -> Json.obj("$oid" -> id))).map { lastError =>
      Ok(Json.obj("msg" -> "Item deleted"))
    }.recover {
      case e => BadRequest(Json.obj("msg" -> e.getMessage()))
    }
  }

}