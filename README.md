# Resource Service #
## Developer: Anastasiya Oleshko ##
### Objectives of this application: ###
* receive an MP3 file and save it to the database;
* extract metadata from an MP3 file and send it to another service for saving in another database;
* return a given object stored in the database by id;
* delete an object from the database by id and send a deletion request to another service that stores object metadata.

### Technology stack ###
* Java version 17
* Spring Boot version 3.1.5
* Spring Data JPA
* Spring WEB MVC
* Spring WEB Flux
* Validator
* Lombok version 1.18.30
* PostgreSQL version 42.6.0
* Gradle version 8.4

Ports used:
* 8080 - for start application
* 7003 - for Postgresql

### To start a Docker container, you need to run the command on the command line: ###
docker run --name resources-db -e POSTGRES_PASSWORD=root -e POSTGRES_DB=resources -d -p 7003:5432 postgres