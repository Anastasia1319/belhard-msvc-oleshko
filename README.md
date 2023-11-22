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
* Apache tika version 2.9.1
* Mapstruct version 1.5.5.Final

### All services and databases will be packaged in their own containers and launched on standard ports: ###
* database (resource-service-db and song-service-db) - ports 5432;
* Song-service and Resource-service - ports 8080.

### Each service and database can be accessed externally through assigned ports: ###
* 7001 - Resource-service
* 7000 - Song-service
* 7003 - resource-service-db
* 7002 - song-service-db


### After restarting services and/or deleting containers and/or images, data from databases is saved in the following volumes: ###
- from song-service-db in song-service-db volume;
- from resource-service-db in resource-service-db volume


All environment variables are placed in the .env file. 

### To start an application and containers, you need to run the command on the command line: ###
.\gradlew build
docker compose up

### To stop an application and containers and remove Docker containers and images, you need to run the command on the command line: ###
docker compose down
docker rmi $(docker image ls -q --filter reference=belhard-msvc-oleshko-*)
