### Incident Reports service

REST APIs for creating, editing and deleting Incident Reports 
related to an application running in production.

#### This realisation uses:

* JKD 17
* Spring Boot 2.6.6
* [Lombok](https://projectlombok.org/) for generating some of code
* [jjwt](https://github.com/jwtk/jjwt) for the easiest JWT handling
* H2 as a database layer (without migrations - all tables will be created after start of app)

#### How to run this project
##### By Gradle:

```shell
gradle clean build && java -jar build/libs/reportService-0.0.1-SNAPSHOT.jar
```

##### By Docker

At first we need to build an image:

```shell
docker build --build-arg JAR_FILE=build/libs/\*.jar -t sh0om/switch-task .
```

Then we can run our app:

```shell
docker run -d -p 8080:8080 sh0om/switch-task
```

#### First steps

If we want to do something with this API, we need to authorize before it. For each our action validation I've decided
to use JWT tokens. By the way, after starting of application, one user (_admin_) will be created in the database.

##### Authorisation
```shell
curl --request POST \
  --url http://localhost:8080/auth/login \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=676AB374C9CECBDE392481C4C99948E9 \
  --data '{
	"username": "admin",
	"password": "admin"
}'
```


