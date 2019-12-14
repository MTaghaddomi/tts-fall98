# TTS_FALL98

پروژه تحلیل و طراحی سیستم



run project: 
./mvnw spring-boot:run

localhost:8000/

**_#api(user requests):_**

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

**get classes which user joined**

GET
/users/myClasses

header: "Auth" => value=token

response: list of ClassroomGeneralInfo{name:string, lesson:string, teacher:string}


**_#api(class requests):_**

**create new class**

POST
/classrooms

header: "Auth" => value=token

request body: {name:string, description:string, lesson:{name:string, description:string}}

response: {name:string, lesson:string, teacher:string}

**update specific class**

PUT
/classrooms/{classroomName}

(replace {classroomName} with the classroom's name)

header: "Auth" => value=token

request body: {name:string, description:string, lesson:{name:string, description:string}, assistants:(...), students:(...)}

students,assistants: list of userInfo{firstName:string, lastName:string, email:string}

response: {name:string, lesson:string, teacher:string}


**delete specific class**

DELETE
/classrooms/{classroomName}

(replace {classroomName} with the classroom's name)

header: "Auth" => value=token

response: 200-OK if successfully deleted




**get classroom details**

GET
/classrooms/{classroomName}

(replace {classroomName} with the classroom's name)

header: "Auth" => value=token

response: list of ClassroomDetailInfo{name:string, description:string, lesson:(...), teacherInfo:(...), studentsInfo:(...)}

lesson:{name:string, description:string}

teacherInfo:{firstName:string, lastName:string, email:string}

studentsInfo:list of studentInfo{firstName:string, lastName:string, email:string}

NOTE: if user is teacher or assistant, will resive this list, otherwise this list will be null

**get classroom's exercises**

GET
/classrooms/{classroomName}/exercises

(replace {classroomName} with the classroom's name)

header: "Auth" => value=token

response: list of exerciseInfo{id:long, subject:string}

NOTE: is no exercise exist for the user(who sends request), list will be null


**_#api(exercise requests):_**


**create new exercise**

POST
/exercise/{classroomName}

(replace {classroomName} with the classroom's name)

header: "Auth" => value=token

request body: {subject:string, description:string, deadline:timestamp, lateDeadline:timestamp, accessLevel}

response: {id:long, subject:string, description:string, deadline:timestamp, lateDeadline:timestamp, accessLevel, fileUrls{list of string}}

**get detail info of specific exercise**

GET
/exercise/{id}

(replace {id} with the exercise's id)

header: "Auth" => value=token

response: {id:long, subject:string, description:string, deadline:timestamp, lateDeadline:timestamp, accessLevel, fileUrls:list of string}


**update specific exercise**

PUT
/exercise/{id}

(replace {id} with the exercise's id)

header: "Auth" => value=token

request body: {subject:string, description:string, deadline:timestamp, lateDeadline:timestamp, accessLevel}

response: {id:long, subject:string, description:string, deadline:timestamp, lateDeadline:timestamp, accessLevel, fileUrls:list of string}


**delete specific exercise**

DELETE
/exercise/{id}

(replace {id} with the exercise's id)

header: "Auth" => value=token

response: 200-OK if successfully deleted


**get all submitted answers of specific exercise**

GET
/exercise/{id}/answers

(replace {id} with the exercise's id)

header: "Auth" => value=token

response: list of strings(file URLs)



**_#api(answer requests):_**

**submit new answer**

POST
/exercise/answer/{exerciseId}

(replace {exerciseId} with the exercise's id)

header: "Auth" => value=token

request body: {text:string}

response: {id:long, text:string, fileUrls:list of strings}


**update specific answer**

PUT
/exercise/answer/{answerId}

(replace {answerId} with the answer's id)

header: "Auth" => value=token

request body: {text:string}

response: {id:long, text:string, fileUrls:list of strings}


**delete specific answer**

DELETE
/exercise/answer/{answerId}

replace {answerId} with the answer's id)

header: "Auth" => value=token

response: 200-OK if successfully deleted


**get answer detail info**

GET
/exercise/answer/{answerId}

(replace {answerId} with the answer's id)

header: "Auth" => value=token

response: {id:long, text:string, fileUrls:list of strings}


