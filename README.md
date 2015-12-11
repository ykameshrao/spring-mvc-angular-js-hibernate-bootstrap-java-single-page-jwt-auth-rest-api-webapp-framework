# Spring AngularJS Java WebApp Template Project

### Introduction ###
Now bringing you the much needed and long pending update to my previous Java Web App Template that I was trying to put together for quite some time now where I am showcasing a template project that can be taken as is to begin developing a modern AngularJS web app backed by Spring and Java.

The projects brings together Java, Spring 4, Spring MVC 4, Spring JPA, Hibernate, Angular JS, Twitter Bootstrap, JWT Token Authentication technology in a clean easy to use and bootstrap fashion. Dowanload and start building your next big thing in Java. Free your mind of scalability and other scares.

### Whats in the Project? ###
The project includes all the code required to create and authenticate a new user including backend models, entities, repositories, service, controller code and frontend forms and relevant javascript validations. Refer the screenshots for getting some idea of the functionality. Also implemented is a ready to use form validation setup in javascript, alongwith URL redirection based on authenticated and un-authenticated sections/endpoints of the website. Also implemented is the simple JWT token based authentication of the REST API endpoints.

The project organises the code in the following sections-

1. Framework - java package housing the common data, dao, service, common code classes to generealize most of the boilerplate code using Java generics and other similar rich features. 
2. Model - java package housing the code related to creating database entities and repository classes defining code to access these entities from database using Hibernate and Spring JPA.
3. Service - java package housing the code defining the various methods specific to your business logic to manipulate the data accordingly.
4. Interceptor - Spring AOP in action here to remove redundant boilerplate code to handle and respond to exceptions in a standard way and also collect performance metrics of the API and service. Refer: WebAppMetricsInterceptor.java, WebAppExceptionAdvice.java
5. Auth - package housing the filter code to process and handle JWT Token based authentication of the REST API requests.
6. Core - package housing the code associated with running a scheduled set of background jobs. The idea with this code peice is to enable offload asynchronous processing like sending mails, uploading files to S3, etc. It needs more work to better setup the thread pools, etc. The code present here today shows a small coding problem I did to create a priority based job runner priortized using Category they belong to. This also shows a job queue that can be persisted to database for execution of jobs without missing.
7. Categories - The category entity shows a self link entity object that lets you create categories and sub categories of categories using a single table.
8. AngularJS App - the entire AngularJS app is placed in the webapp directory and further organizes the JavaScript code into services, controllers, views and models giving a convention-over-configuration setup to start building your AngularJS powered Web UI. The entire purpose of this code peice is to get you bootstrapped and running ASAP. The primary single page app (SPA) is loaded in index.html. 



### Running ###
Download the code and build the project the same way as you would do for any mven project. I have currently plugged in the test-jdbc xml fil in the configuration that creates and runs a InMemory H2 database. I also included a standard MySQL Spring JDBC xml file. Use that one by changing the context file in web.xml and after changing the parameters there according to your local MySQL setup.

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

I heavily reference many external links and books while creating this project apart from my own expereince. I am listing a few of them here. If I missed something, please accept my deepest apologies. I also spent a long time trying to implement Spring Security based authentication but ultimately failed. My whole approach here was to bring everything togather in a simple and easy and ready to reuse package. I have tried to avoid any direct copying of code though, since most of it required some kind of changes. Most of the spring security code is copied from elsewhere but you would find it in the unused package since it was never really used in the functioning website.

1. AngularJS In Action
2. https://github.com/jwtk/jjwt


### Enjoy!! ###


GitHub Repo for the project: https://github.com/ykameshrao/Spring-AngularJS-Java-Web-App-Template-Project

### Blog Post: http://www.orangeapple.org/post/134959159212/spring-angularjs-java-web-app-template-project ###
