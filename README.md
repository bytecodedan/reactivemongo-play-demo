#reactivemongo-play-demo
A simple RESTful application demonstrating ReactiveMongo usage with Play Framework.  It tracks comments and ratings of an item.  An item can be anything (book, movie, automobile, etc).  Aside from basic model validation, there are no other restrictions at the moment.  For example, a user can submit several reviews per item.

##Technologies Used
* Play Framework 2.2.1
* MongoDB 2.4.6
* Play ReactiveMongo plugin 0.10.2

##End-points:

###Users
######Routes
```scala
GET       /users                    # list of all users                
POST      /users                    # creates a user    
GET       /users/:userId            # fetches a user       
PUT       /users/:userId            # updates a user       
DELETE    /users/:userId            # deletes a user
GET       /users/:userId/reviews    # lists of all reviews from a user
```
######Example Request Body (POST & PUT)
```json
{
 "firstName": "Jane",
 "lastName": "Doe",
 "email": "jdoe@test.com",
 "username": "jdoe",
}
```
######Example cURL commands
```
curl -i -X GET http://localhost:9000/users
curl -i -X POST http://localhost:9000/users -H 'Content-Type: application/json' -d '{"firstName":"Rick", "lastName":"James", "email":"rjames@test.com", "username":"rjames"}'
curl -i -X GET http://localhost:9000/users/5375fec37d000038c8406325
curl -i -X PUT http://localhost:9000/users/5375fec37d000038c8406325 -H 'Content-Type: application/json' -d '{"firstName":"Rick", "lastName":"James", "email":"rjames@test.com", "username":"rjames"}'
curl -i -X DELETE http://localhost:9000/users/5375fec37d000038c8406325
```

###Items
######Routes
```scala
GET       /items             # list of all items                
POST      /items             # creates an item    
GET       /items/:itemId     # fetches an item       
PUT       /items/:itemId     # updates an item       
DELETE    /items/:itemId     # deletes an item
```
######Example Request Body (POST & PUT)
```json
{
 "name": "ABC Frozen Yogurt"
}
```
######Example cURL commands
```
curl -i -X GET http://localhost:9000/items
curl -i -X POST http://localhost:9000/items -H 'Content-Type: application/json' -d '{"name":"ABC Ice Cream"}'
curl -i -X GET http://localhost:9000/items/5376035b7d000002ff406328
curl -i -X PUT http://localhost:9000/items/5376035b7d000002ff406328 -H 'Content-Type: application/json' -d '{"name":"XYZ Ice Cream"}'
curl -i -X DELETE http://localhost:9000/items/5376035b7d000002ff406328
```

###Reviews
######Routes
```scala
GET       /items/:itemId/reviews               # list of all items                
POST      /items/:itemId/reviews               # creates a review of an item    
GET       /items/:itemId/reviews/:reviewId     # fetches a review of an item      
PUT       /items/:itemId/reviews/:reviewId     # updates a review of an item        
DELETE    /items/:itemId/reviews/:reviewId     # deletes a review of an item 
```
######Example Request Body (POST & PUT)
```json
{
 "item": {
   "$oid": "53756c1b7d00005ebd406317"
 },
 "user": {
   "$oid": "537569297d0000f0ac406316"
 },
 "rating": 5,
 "text": "Awesome product",
}
```
######Example cURL commands
```
curl -i -X GET http://localhost:9000/items/5376055a7d000081f940632a/reviews
curl -i -X POST http://localhost:9000/items/5376055a7d000081f940632a/reviews -H 'Content-Type: application/json' -d '{"item":{"$oid":"5376055a7d000081f940632a"}, "user":{"$oid":"5375f98e7d0000627c406321"}, "rating":4, "text":"What a fantastic product!"}'
curl -i -X GET http://localhost:9000/items/5376055a7d000081f940632a/reviews/537606f67d0000d30a40632b
curl -i -X PUT http://localhost:9000/items/5376055a7d000081f940632a/reviews/537606f67d0000d30a40632b -H 'Content-Type: application/json' -d '{"item":{"$oid":"5376055a7d000081f940632a"}, "user":{"$oid":"5375f98e7d0000627c406321"}, "rating":2, "text":"I changed my mind; what a horrible product!"}'
curl -i -X DELETE http://localhost:9000/items/5376055a7d000081f940632a/reviews/537606f67d0000d30a40632b
```
