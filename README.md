#reactivemongo-play-demo
=======================

A simple RESTful application demonstrating ReactiveMongo usage with Play Framework.  It tracks comments and ratings per item.

This application has the following end-points:

###Users
```scala
GET       /users                    #list of all users                
POST      /users                    #creates a user    
GET       /users/:userId            #fetches a user       
PUT       /users/:userId            #updates a user       
DELETE    /users/:userId            #deletes a user
GET       /users/:userId/reviews    #lists of all reviews from a user
```

###Items
```scala
GET       /items             #list of all items                
POST      /items             #creates an item    
GET       /items/:itemId     #fetches an item       
PUT       /items/:itemId     #updates an item       
DELETE    /items/:itemId     #deletes an item
```

###Reviews
```scala
GET       /items/:itemId/reviews               #list of all items                
POST      /items/:itemId/reviews               #creates an item    
GET       /items/:itemId/reviews/:reviewId     #fetches an item       
PUT       /items/:itemId/reviews/:reviewId     #updates an item       
DELETE    /items/:itemId/reviews/:reviewId     #deletes an item
```
