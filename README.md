<h1 align="center"> Spring Boot Challenge from Meli Bootcamp </h1>

<p align="justify"> Social Meli Rest API. Clients will be able to follow their favorite sellers and find out all the news they themselves post. Developed with Java 11, Spring Boot (Rest, Data and Validate) and H2 Database. </p>

### Postman Collection:

- https://www.getpostman.com/collections/e166a8067cc4c0aad56e

Use the link above on Postman to import the collection

## Requirements

For building and running the application you need:

- [JDK 11]
- [Maven 3](https://maven.apache.org)

## Instructions

To access the in-memory database H2:

- Run the project
- Access http://localhost:8080/h2-console

To access the documentation:
- Run the project
- Access [Swagger Documentation](http://localhost:8080/swagger-ui.html#/)

To test the application:
- Run the project
- Access the postman collection

Use the postman colletion to test the application and the h2 database to check persisted data

## Endpoints

### [GET] Setup Project
Create Users (Clients and Sellers).

### [POST] User Follows Seller

Client or Seller can follow Sellers

### [GET] Followers Count

Returns the number of users who follow a seller

### [GET] Followers List with Optional Param

Returns a seller's list of followers

### [GET] Followed List

Returns the list of sellers that a user follows

### [POST] Create New Post

Create a new post

### [GET] Products from Followed List

### [POST] User unfollow Seller

User unfollow a seller

### [POST] Create new Promo Post

Create a new promotional post

### [GET] Number of Seller Promo Posts

Return the number of promotional posts from a seller

### [GET] Seller Promo Post List

Return the list of promotional posts from a seller
