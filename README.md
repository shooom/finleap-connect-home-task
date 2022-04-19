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
**Result**
```json
{
	"username": "admin",
	"token": "{jwt_token}"
}
```
You need to use _token_ value for all other requests to the app.

There are 2 main parts of this app:
1. Users
2. Reports

#### Users
##### Add new user
```shell
curl --request POST \
  --url http://localhost:8080/users \
  --header 'Authorization: Bearer_{jwt_token}' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=676AB374C9CECBDE392481C4C99948E9 \
  --data '{
	"username": "user_5",
	"password": "ps123",
	"roles": ["USER"]
}'
```
**Result**
```json
{
  "id": 4,
  "username": "user_5",
  "authorities": [
    {
      "authority": "USER"
    }
  ]
}
``` 

##### Get all users
```shell
curl --request GET \
  --url http://localhost:8080/users \
  --header 'Authorization: Bearer_{jwt_token}' \
  --cookie JSESSIONID=676AB374C9CECBDE392481C4C99948E9
```
**Result**
```json
[
  {
    "id": 3,
    "username": "admin",
    "authorities": [
      {
        "authority": "USER"
      },
      {
        "authority": "ADMIN"
      }
    ]
  }
]
``` 

##### Update user
```shell
curl --request PUT \
  --url http://localhost:8080/users/1 \
  --header 'Authorization: Bearer_{jwt_token}' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=676AB374C9CECBDE392481C4C99948E9 \
  --data '{
	"username": "user_new_user",
	"password": "ps123",
	"roles": ["ADMIN", "USER"]
}'
```
**Result**
```json
{
  "id": 4,
  "username": "user_new_user123",
  "authorities": [
    {
      "authority": "USER"
    }
  ]
}
``` 

##### Delete user
```shell
curl --request DELETE \
  --url http://localhost:8080/users/1 \
  --cookie JSESSIONID=676AB374C9CECBDE392481C4C99948E9
``` 

#### Reports
##### Add new report
```shell
curl --request POST \
  --url http://localhost:8080/reports \
  --header 'Authorization: Bearer_{jwt_token}' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=676AB374C9CECBDE392481C4C99948E9 \
  --data '{
	"title": "Third report",
	"assigneeId": 4
}'
```
**Result**
```json
{
  "id": 5,
  "createdAt": "2022-04-19T21:14:20.966474Z",
  "updatedAt": "2022-04-19T21:14:20.966479Z",
  "assignee": {
    "id": 4,
    "username": "user_5"
  },
  "title": "Third report",
  "author": {
    "id": 3,
    "username": "admin"
  },
  "status": "ASSIGNED"
}
``` 

##### Add new report
```shell
curl --request POST \
  --url http://localhost:8080/reports \
  --header 'Authorization: Bearer_{jwt_token}' \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=676AB374C9CECBDE392481C4C99948E9 \
  --data '{
	"title": "Third report",
	"assigneeId": 4
}'
```
**Result**
```json
{
  "id": 5,
  "createdAt": "2022-04-19T21:14:20.966474Z",
  "updatedAt": "2022-04-19T21:14:20.966479Z",
  "assignee": {
    "id": 4,
    "username": "user_5"
  },
  "title": "Third report",
  "author": {
    "id": 3,
    "username": "admin"
  },
  "status": "ASSIGNED"
}
``` 

##### Get list of reports
```shell
curl --request GET \
  --url http://localhost:8080/reports \
  --header 'Authorization: Bearer_{jwt_token}' \
  --cookie JSESSIONID=676AB374C9CECBDE392481C4C99948E9
```
**Result**
```json
[
  {
    "id": 5,
    "status": "ASSIGNED",
    "title": "Third report",
    "author": {
      "id": 3,
      "username": "admin"
    },
    "assignee": {
      "id": 4,
      "username": "user_new_user123"
    },
    "createdAt": "2022-04-19T22:22:21.692931Z",
    "updatedAt": "2022-04-19T22:22:21.692941Z"
  }
]
``` 

##### Change status of report (**close report**)
```shell
curl --request GET \
  --url http://localhost:8080/reports/5/close \
  --header 'Authorization: Bearer_{jwt_token}' \
  --cookie JSESSIONID=676AB374C9CECBDE392481C4C99948E9
```

##### Delete report
```shell
curl --request DELETE \
  --url http://localhost:8080/reports/4 \
  --header 'Authorization: Bearer_{jwt_token}' \
  --cookie JSESSIONID=676AB374C9CECBDE392481C4C99948E9
```