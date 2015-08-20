
##Build

```maven
    mvn package
```
##Run
To run this project there are two wys:

1. Using executable java file, which is created by Spring Web. This command will create server end deploy application on port 8443 which can be changed in the *application.properties* file 
```java
    java -jar target/SpringTwitter-1.0-SNAPSHOT.war
```
2. Using Apache Tomcat server. 
```command
    copy java -jar target/SpringTwitter-1.0-SNAPSHOT.war into webapps folder of Apache Tomcat server
```
