# Spring MVC AngularJS Hibernate Bootstrap JWT Token Authenticated REST API Single Page Java Webapp Framework

![Home Page](https://github.com/ykameshrao/spring-mvc-angular-js-hibernate-bootstrap-java-single-page-jwt-auth-rest-api-webapp-framework/blob/master/screenshots/HomePage.png)

### Blog Post ###
http://www.orangeapple.org/post/134959159212/spring-mvc-angularjs-hibernate-bootstrap-java

### Introduction ###
I am showcasing a webapp template project framework that can be used AS-IS to begin developing a modern AngularJS Single Page Web App backed by Spring and Java. The projects brings together Java, Spring 4, Spring MVC 4, Spring JPA, Hibernate, Angular JS, Twitter Bootstrap, JWT Token Authentication technology in a clean easy to use fashion. Download and start building your next big thing in Java. Free your mind of scalability and other web app development scares.

The stack here consists of Spring MVC, AngularJS, Hibernate, Spring JPA, Twitter Bootstrap, JWT Token Authentication, RESTful API. All of these are nicely brought togather with a bunch of Java and JS boilerplate code meant to give a template framework allowing the flow of control in conventional way. Consider this as a pre-configured Java Template Project Framework promoting the Convention over Configuration idea of Design.

### Whats in the Project? ###
The project includes all the code required to create and authenticate a new user including backend models, entities, repositories, service, controller code and frontend forms and relevant javascript validations. Refer the screenshots for getting some idea of the functionality. Also implemented is a ready to use form validation setup in javascript, along with URL redirection based on authenticated and un-authenticated sections/endpoints of the website. Also implemented is the simple JWT token based authentication of the REST API endpoints.

The project organises the code in the following sections-

1. Framework - java package housing the common data, dao, service, common code classes to generalize most of the boilerplate code using Java generics and other similar rich features. 
2. Model - java package housing the code related to creating database entities and repository classes defining code to access these entities from database using Hibernate and Spring JPA.
3. Service - java package housing the code defining the various methods specific to your business logic to manipulate the data accordingly.
4. Interceptor - Spring AOP in action here to remove redundant boilerplate code to handle and respond to exceptions in a standard way and also collect performance metrics of the API and service. Refer: WebAppMetricsInterceptor.java, WebAppExceptionAdvice.java
5. Auth - package housing the filter code to process and handle JWT Token based authentication of the REST API requests.
6. Core - package housing the code associated with running a scheduled set of background jobs. The idea with this code piece is to enable offload asynchronous processing like sending mails, uploading files to S3, etc. It needs more work to better setup the thread pools, etc. The code present here today shows a small coding problem I did to create a priority based job runner prioritized using Category they belong to. This also shows a job queue that can be persisted to database for execution of jobs without missing.
7. Categories - The category entity shows a self link entity object that lets you create categories and sub categories of categories using a single table.
8. AngularJS App - the entire AngularJS app is placed in the webapp directory and further organizes the JavaScript code into services, controllers, views and models giving a convention-over-configuration setup to start building your AngularJS powered Web UI. The entire purpose of this code peice is to get you bootstrapped and running ASAP. The primary single page app (SPA) is loaded in index.html. 



### Executing ###
Download the code and build the project the same way as you would do for any maven project. I have currently plugged in the test-jdbc xml file in the configuration that creates and runs a InMemory H2 database. I also included a standard MySQL Spring JDBC xml file. Use that one by changing the context file in web.xml and after changing the parameters there according to your local MySQL setup.

###Technologies###
  -  Java
  -  Spring
  -  Spring MVC
  -  Spring AOP
  -  Hibernate  
  -  Angular JS
  -  Twitter Bootstrap
  -  JWT Token Auth
  -  Domain Driven Model
  -  Maven

### References ###
I have heavily referenced many external links and books while creating this project apart from my own experience. I am listing a few of them here. If I missed something, please accept my deepest apologies. I also spent a long time trying to implement Spring Security based authentication but ultimately failed. My whole approach here was to bring everything together in a simple and easy and ready to reuse package. I have tried to avoid any direct copying of code though, since most of it required some kind of changes. Most of the spring security code is copied from elsewhere but you would find it in the unused package since it was never really used in the functioning website.

1. AngularJS In Action
2. https://github.com/jwtk/jjwt

### ScreenShots ###
![Home Page](https://github.com/ykameshrao/spring-mvc-angular-js-hibernate-bootstrap-java-single-page-jwt-auth-rest-api-webapp-framework/blob/master/screenshots/HomePage.png)

![Registration Page w/ Validation](https://github.com/ykameshrao/spring-mvc-angular-js-hibernate-bootstrap-java-single-page-jwt-auth-rest-api-webapp-framework/blob/master/screenshots/RegisterWithValidationFail.png)

![Registration Page](https://github.com/ykameshrao/spring-mvc-angular-js-hibernate-bootstrap-java-single-page-jwt-auth-rest-api-webapp-framework/blob/master/screenshots/RegisterWithValidationSuccess.png)

![Dashboard Page](https://github.com/ykameshrao/spring-mvc-angular-js-hibernate-bootstrap-java-single-page-jwt-auth-rest-api-webapp-framework/blob/master/screenshots/Dashboard.png)

![API Response Structure](https://github.com/ykameshrao/spring-mvc-angular-js-hibernate-bootstrap-java-single-page-jwt-auth-rest-api-webapp-framework/blob/master/screenshots/APIResponse.png)
### Enjoy!! ###


GitHub Repo for the project: https://github.com/ykameshrao/spring-mvc-angular-js-hibernate-bootstrap-java-single-page-jwt-auth-rest-api-webapp-framework

### Blog Post: http://www.orangeapple.org/post/134959159212/spring-mvc-angularjs-hibernate-bootstrap-java ###
