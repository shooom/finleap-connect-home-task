### Incident Reports service

REST APIs for creating, editing and deleting Incident Reports 
related to an application running in production.

#### This realisation uses:

* JKD 17
* Spring Boot 2.6.6
* [Lombok](https://projectlombok.org/) for generating some of code
* [jjwt](https://github.com/jwtk/jjwt) for the easiest JWT handling
* H2 as a database layer

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

