
# Project goal:
Rest Api with single endpoint whose task will be to return a list of all public repositories of  
the indicated Github user (sent as a parameter)


## Default port:
8080

## End-points docs:
http://localhost:8080/v2/api-docs

## Run app:
``
java -jar task-0.0.1-SNAPSHOT.jar
``  
or using maven plugin:  
``mvn spring-boot:run``



## User access configuration:  
by adding an entry to the file:
1. Json file, path configurable by application.properties - credentials.path
2. Default location: ``resources/credentials.json``
3. Format:
```
[
  {
    "username": "tom",
    "password": "$2a$11$d37aPIrJdQDC8WmZCS01qeUpx3xeFxo/lvARq.nt8e/0MNhRXXegW" //abcde
  }
]
```
**Password encode** - _BCryptPasswordEncoder_, **strength:11**

test user:  
username: tom  
password: abcde

username: user  
password: pass  

