# TTS_FALL98

پروژه تحلیل و طراحی سیستم



run project: 
./mvnw spring-boot:run

localhost:8000/

**_#api:_**

**sign-up:**

POST
/users

request body {username, password}

response {token, username}


**sign-in:**

POST
/users/login

request body {username, password}

response {token, username}


**get profile:**

GET
/users/{username}

(replace {username} with the username)

header: "Auth" => value=token

response {username, firstName, lastName, email, birthday, phoneNumber}

**edit profile:**

PUT
/users/{username}

(replace {username} with the username)

header: "Auth" => value=token

request body {firstName, lastName, email, birthday, phoneNumber}

response {username, firstName, lastName, email, birthday, phoneNumber}

**delete account:**

DELETE
/users/{username}

(replace {username} with the username)

header: "Auth" => value=token

response: 200-OK if successfully deleted

