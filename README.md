

default port:
8080

end-points docs:
http://localhost:8080/v2/api-docs

run app:
java -jar task-0.0.1-SNAPSHOT.jar

or using maven plugin:
mvn spring-boot:run



configure user access - add credentials to file:
1. json file, path configurable by application.properties - credentials.path
2. default location resources/credentials.json
3. Format
[
  {
    "username": "tom",
    "password": "$2a$11$d37aPIrJdQDC8WmZCS01qeUpx3xeFxo/lvARq.nt8e/0MNhRXXegW" //abcde
  }
]

password encode - BCryptPasswordEncoder, strength:11

test user:
username: tom
password: abcde

username: user
password: pass

