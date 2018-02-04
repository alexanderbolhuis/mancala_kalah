# mancala_kalah

Kalah board game in Spring Boot for Bol.com Tech Assignment

https://en.wikipedia.org/wiki/Kalah

## Tech Stack
Tools & Techniques used

Backend:
* Java 1.8
* PostgreSQL 10.1
* Spring Boot
* Project Lombok
* Hibernate
* JUnit/Mockito

Frontend:
* Angularjs
* SockJs
* Stomp
* JQuery
* Bootstrap

## How to run
Spring Boot will create tables and add users to start playing. 

### Setup PostgreSQL
For Manjaro/Arch:

    sudo pacman -S postgresql
    sudo systemctl start postgresql.service

Setup db:

    su postgres
    psql
    postgres=# CREATE USER kalah WITH PASSWORD 'kalah'
    postgres=# CREATE DATABASE kalah
    
### Run with maven

    mvn test
    mvn spring-boot:run
    
## Use app
### Location

    localhost:8080
    
### Login with

    U: alexander
    P: alexander
    
### Or with
    
    U: irene
    P: irene
    
### Or with
    
    U: ralph
    P: ralph
    
### Or with
    
    U: bart
    P: bart
    
### Or with
    
    U: thijmen
    P: thijmen
    
## Potential improvements
* Improve error handling
* Move controllers to WebSocket controllers
* Improve Tests + Coverage
* Add multiple board layouts
* Frontend design (especially Login Page)
* More?
 
    
