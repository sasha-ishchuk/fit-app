# *FitApp*

***My first biggest project created with Spring and Java***

Application to track your daily tasks (workouts also) which allows you to 
keep your routine/health/fitness/nutrition/diet information in one place. 
For now include: BMI chart & task planner (todo list).

> NOTE: ***FitApp*** development started 24.08.2022 - it's **IN PROCESS FOR NOW**

---

## Table of Contents
1. [General information](#1-general-information)
    - [Scope of functionalities](#11-scope-of-functionalities)
2. [Endpoints](#2-endpoints)
3. [Technologies](#3-technologies)
4. [Requirements](#4-requirements)
    * [MySQL properties](#41-mysql-propertie)
5. [Install](#5-install)
6. [Run](#6-run)
7. [How to use](#7-how-to-use)
8. [Contact](#8-contact)
---

## 1. General information

#### *SWAGGER UI: http://localhost:7777/swagger-ui/*

This project was written in Java and Spring,
the development of which I'm going to finish in next few weeks. 
The program is a fully web-based application.

### 1.1. Scope of functionalities
**The application includes functionalities such as:**

* Registration and login **user** accounts
   * Registration with first/last name, email and password
   * Password confirmation in registration process
   * ***Remember me*** button on login page
* Error page for nonexistent pages in application

**Features in progress:**
* User page (empty for now)
* BMI chart
* ToDo list

####  *TO BE CONTINUED...*

---
## 2. Endpoints
####  *TO BE CONTINUED...*

---

## 3. Technologies
* **Java** version: 17
* **Maven** version: 3.8.1
* **SpringBoot** version: 2.7.2
* **MySQL** database 
* **Thymeleaf** template engine
---

## 4. Requirements
For building and running the application you need:
* JDK 17
* Maven 3
* MySQL

### 4.1. MySQL properties
> **NOTE**: before you start, you need to create **"fitapp_db"** schema in your 
> MySQL Workbench - all tables will be created by application automatically

> **IMPORTANT**: check **password** for your ***root*** user in your MySQL Workbench
> and compare it with **password** in *application.properties* 
> [file](src/main/resources/application.properties). Change it for your password, 
> because access for user 'root'@'localhost' will be denied.
>
>```shell
>spring.datasource.password=[your_password]
>```

>**ALSO**: check another **datasource** properties, maybe you need to change port,
> host or username
>```shell
>spring.datasource.url=jdbc:mysql://localhost:3306/fitapp_db
>spring.datasource.username=root
>```
---

## 5. Install
To install the application follow the instruction below:
* Open the project in your IDE
* Go to Maven toolbar
* Execute *install* option in the *Lifecycle* menu
---

## 6. Run

**There are several ways to run a Spring application on your local machine**.
1. Open the project in your IDE and execute the main method in the *FitApplication.java* class
   (link to [file](src/main/java/com/sasha/fitapp/FitApplication.java)).

```java
@SpringBootApplication
public class FitApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitApplication.class, args);
    }

}
```

2. Use the Spring Boot Maven plugin by executing the command below:

```shell
mvn spring-boot:run
```

3. Run [installed](#4-install) application in terminal:

```shell
cd target
java -jar fit-app-0.0.1-SNAPSHOT.jar
```

>**NOTE**: if you want to run the application on the specific port,
> execute the command:
> ```shell
> java -jar -Dserver.port=[your port] fit-app-0.0.1-SNAPSHOT.jar
> ```
> **OR**: add to *application.properties* [file](src/main/resources/application.properties)
> this property:
> ```shell
> server.port=[your port]
> ```
---

## 7. How to use
After running ***FitApp*** will start on:
http://localhost:7777

>**NOTE**: if you choose another port while run the application
> from terminal or change port in *application.properties* file,
> go to: http://localhost:[your port]
---

## 8. Contact
* [LinkedIn](https://www.linkedin.com/in/sasha-ishchuk/)
* sasha.ishchuk@gmail.com
