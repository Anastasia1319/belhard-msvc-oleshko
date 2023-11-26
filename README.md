# Resource Service #
## Developer: Anastasiya Oleshko ##
### Objectives of this application: ###
* receive an MP3 file and save it to the Amazon S3 service;
* save key from Amazon S3 service in resource-service-db;
* extract metadata from an MP3 file and send it to song-service for saving in another song-service-db;
* return a given object stored from Amazon S3 service by id;
* return all objects from Amazon S3 service;
* delete an object from Amazon S3 service by id, send a deletion request to Song-service that stores object metadata and delete key from Amazon S3 service in resource-service-db.

### Brief description of the application ###
The downloaded MP3 file comes to the Resource-server, in which:
1. generates a key for saving the song in the Amazon S3 service;
2. the generated key is saved in the resource-service-db database, where it is assigned an id;
3. Information is extracted from the MP3 file, the resourceId value is set, and this object is transferred to the Song-service using Discovery-server.

The MetaData received by the Song-service is saved in the song-service-db database.

When requesting a song by id, the Resource-service requests the key value for the Amazon S3 service from the resource-service-db database, where it requests the song stored in it. MetaData from Song-service is not requested.

When Resource-service receives a request to delete a song:
1. A key for Amazon S3 service is requested from the resource-service-db database;
2. Using the received key, the song is deleted from the Amazon S3 service;
3. The id of the object from resource-service-db is transferred to Song-service using Discovery-server (resourceId in song-service);
4. in Song-service, MetaData is deleted from the song-service-db database;
5. a request is sent to the resource-service-db database to delete this object.

Service Discovery is implemented through Client-Side Discovery and Self-Registration using Eureka Client/Server and Spring Cloud.

LocalStack platform is used instead of Amazon S3.

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
* Amazon AwsSDK (bom:2.20.109)
* Spring Cloud Netflix Eureka version 2022.0.4

### All services and databases will be packaged in their own containers and launched on standard ports: ###
* database (resource-service-db and song-service-db) - ports 5432;
* Song-service and Resource-service - ports 8080;
* Discovery-server - port 8761;
* LocalStack - port 4566.

Song-service launched in three replicas. Balancing requests to song-service is done using LoadBalancerClient from Spring Cloud Client.

### Services which can be accessed externally through assigned ports: ###
* 7001 - Resource-service
* 8761 - Discovery-server


### After restarting services and/or deleting containers and/or images, data from databases is saved in the following volumes: ###
- from song-service-db in song-service-db volume;
- from resource-service-db in resource-service-db volume.

All environment variables are placed in the .env file. 

### To start an application and containers, you need to run the command on the command line: ###
.\gradlew build
docker compose up

### To stop an application and containers and remove Docker containers and images, you need to run the command on the command line: ###
docker compose down
docker rmi $(docker image ls -q --filter reference=belhard-msvc-oleshko-*)
