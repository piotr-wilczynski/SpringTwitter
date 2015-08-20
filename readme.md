
##Build

```maven
    mvn package
```
##Run
To run this project there are two wys:

**1. Using Spring Boot.** 
This command will create server end deploy application on port *8443* which can be changed in the *application.properties* file
```java
    java -jar target/SpringTwitter-1.0-SNAPSHOT.war
```
**2. Using Apache Tomcat server.**
```command
    copy java -jar target/SpringTwitter-1.0-SNAPSHOT.war into webapps folder of Apache Tomcat server
```
##List of tools
1. [Bootstrap](http://getbootstrap.com/) - Bootstrap is the most popular HTML, CSS, and JS framework for developing responsive, mobile first projects on the web.
2. [Spring](https://spring.io/) - The Spring Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications - on any kind of deployment platform. A key element of Spring is infrastructural support at the application level: Spring focuses on the "plumbing" of enterprise applications so that teams can focus on application-level business logic, without unnecessary ties to specific deployment environments.
3. [Spring Boot](http://projects.spring.io/spring-boot/) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run". We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need very little Spring configuration.
4.  [Spring Social](http://projects.spring.io/spring-social/) - Connect your Spring application with Software-as-a-Service (SaaS) API providers such as Facebook, Twitter, and LinkedIn.
