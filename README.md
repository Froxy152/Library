# Spring Boot LibraryApi Project

This is a sample Java / Maven / Spring Boot it's simple REST,CRUD api.
## How to Run


* Clone this repository
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:
```
       java -jar target/Library-0.0.1-SNAPSHOT.war
```


Once the application runs you should see something like this

```
2024-09-11T20:48:44.516+03:00  INFO 7620 --- [Library] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2024-09-11T20:48:44.570+03:00  INFO 7620 --- [Library] [           main] r.shestakov.Library.LibraryApplication   : Started LibraryApplication in 18.116 seconds (process running for 19.621)
```

### Get information about book, etc.

```
http://localhost:8080/api/v1 - get all books
http://localhost:8080/api/v1/{id} - get by id
http://localhost:8080/isbn/{isbn} - get by isbn
http://localhost:8080/api/v1/library - get all FREE books 
```


### Create a book
```
POST /api/v1/create
Accept: application/json
Content-Type: application/json

{
   "title" : "1984",
   "genre" : "Dystopian",
   "author" : "George Orwell",
   "description" : "A dystopian novel about totalitarianism.",
   "isbn" : "1234567890123",
   "status" : "FREE"

}
RESPONSE: HTTP 201 (Created)
```

### Update a book
```
PUT /api/v1/update/1
Accept: application/json
Content-Type: application/json

{
   "title" : "1984",
   "genre" : "Dystopian",
   "author" : "George Orwell",
   "description" : "A dystopian novel about totalitarianism.",
   "isbn" : "1234567890123",
   "status" : "OCCUPIED"

}

```
### Delete operation
```
DELETE /api/v1/update/1
Accept: application/json
Content-Type: application/json

```

### Sign up and Sign in
#### Sign up
```
POST /api/v1/auth/reg
Accept: application/json
Content-Type: application/json

{
    "username" : "user",
    "password" : "password"
}
RESPONSE: HTTP 201 (Created)
```

#### Sign in

```
POST /api/v1/auth/login
Accept: application/json
Content-Type: application/json

{
    "username" : "user",
    "password" : "password"
}
RESPONSE: HTTP 200 (OK)
{
    "tokenType": "Bearer ",
    "accessToken": "some jwt token",
    "expireTime": "expireTime"
}
```

### Application.properties
````
---
constants.secret = <your secret>
constants.jwtExpired = <your expriretime>
spring.datasource.url=jdbc:postgresql://<your postgre ip or host>/library
spring.datasource.username=<your username>
spring.datasource.password=<your password>
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database=postgresql
spring.jpa.properties.hibernate.boot.allow_jdbc_metadata_access=false
spring.jpa.hibernate.ddl-auto=update
---
```