#reactivemongo-play-demo
========================
A simple RESTful application demonstrating ReactiveMongo usage with Play Framework.  It tracks comments and ratings of an item.  An item can be anything (book, movie, automobile, etc).  Aside from basic model validation, there are no other restrictions at the moment.  For example, a user can submit several reviews per item.

##Technologies Used
* Play Framework 2.2.1
* MongoDB 2.4.6
* Play ReactiveMongo plugin 0.10.2

##End-points:

###Users
```scala
# Routes
# ------
GET       /users                    # list of all users                
POST      /users                    # creates a user    
GET       /users/:userId            # fetches a user       
PUT       /users/:userId            # updates a user       
DELETE    /users/:userId            # deletes a user
GET       /users/:userId/reviews    # lists of all reviews from a user

# JSON (POST & PUT request body)
# ----- 
# {
#   "firstName": "Jane",
#   "lastName": "Doe",
#   "email": "jdoe@test.com",
#   "username": "jdoe",
# }
```

###Items
```scala
# Routes
# ------
GET       /items             # list of all items                
POST      /items             # creates an item    
GET       /items/:itemId     # fetches an item       
PUT       /items/:itemId     # updates an item       
DELETE    /items/:itemId     # deletes an item

# JSON (POST & PUT request body)
# ----- 
# {
#   "name": "ABC Frozen Yogurt",
# }
```

###Reviews
```scala
# Routes
# ------
GET       /items/:itemId/reviews               # list of all items                
POST      /items/:itemId/reviews               # creates an item    
GET       /items/:itemId/reviews/:reviewId     # fetches an item       
PUT       /items/:itemId/reviews/:reviewId     # updates an item       
DELETE    /items/:itemId/reviews/:reviewId     # deletes an item

# JSON (POST & PUT request body)
# ------
# {
#   "item": {
#     "$oid": "53756c1b7d00005ebd406317"
#   },
#   "user": {
#     "$oid": "537569297d0000f0ac406316"
#   },
#   "rating": 5,
#   "text": "Awesome product",
# }
```
