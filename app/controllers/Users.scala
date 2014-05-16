package controllers

import play.modules.reactivemongo.MongoController
import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import scala.concurrent.Future
import org.joda.time.DateTime


object Users extends Controller with MongoController{

  def collection: JSONCollection = db.collection[JSONCollection]("users")

  import models.User

  def index = Action.async { implicit request =>
    val col = collection.find(Json.obj()).cursor[User]

    col.collect[List]().map { users =>
      Ok(Json.obj(
        "msg" -> ("Found " + users.length + " items"),
        "count" -> users.length,
        "users" -> Json.toJson(users)))
    }.recover {
      case e => BadRequest(Json.obj("msg" -> e.getMessage()))
    }
  }

  def create = Action.async(parse.json) { implicit request =>
    request.body.validate[User].map { user =>
      collection.insert(user).map { _ =>
        Ok(Json.obj(
          "msg" -> "Item created",
          "user" -> Json.toJson(user)))
      }
    }.getOrElse(Future.successful(BadRequest(Json.obj("msg" -> "Invalid json"))))
  }

  def find(userId: String) = Action.async { implicit request =>
    collection.find(Json.obj("_id" -> Json.obj("$oid" -> userId))).one[User].map { user =>
      Ok(Json.obj(
        "msg" -> "Item found",
        "user" -> Json.toJson(user)))
    }.recover {
      case e => BadRequest(Json.obj("msg" -> e.getMessage()))
    }
  }

  def update(userId: String) = Action.async(parse.json) { implicit request =>
    request.body.validate[User].map { user =>
      val objectId = Json.obj("_id" -> Json.obj("$oid" -> userId))
      val modifier = Json.obj(
        "$set" -> Json.obj(
          "firstName" -> user.firstName,
          "lastName" -> user.lastName,
          "email" -> user.email,
          "username" -> user.username,
          "updated" -> new DateTime().getMillis))
      collection.update(objectId, modifier).map { _ =>
        Ok(Json.obj(
          "msg" -> "Item updated",
          "user" -> Json.toJson(user)))
      }
    }.getOrElse(Future.successful(BadRequest(Json.obj("msg" -> "Invalid json"))))
  }

  def delete(userId: String) = Action.async { implicit request =>
    collection.remove(Json.obj("_id" -> Json.obj("$oid" -> userId))).map { lastError =>
      Ok(Json.obj("msg" -> "Item deleted"))
    }.recover {
      case e => BadRequest(Json.obj("msg" -> e.getMessage()))
    }
  }

}
