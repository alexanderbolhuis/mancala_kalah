# mancala_kalah

Kalah board game in Spring Boot

### Tech Stack
Tools & Techniques used:
* Java 1.8
* PostgreSQL 10.1
* Maven 3.5.2
* Spring Boot
* Project Lombok
* Hibernate
* JUnit/Mockito
* IntelliJ

### How to run
Spring Boot will create tables and add users to start playing. 

Setup PostgreSQL:

    sudo pacman -S postgresql
    sudo systemctl start postgresql.service

Setup db:

    su postgres
    psql
    postgres=# CREATE USER kalah WITH PASSWORD 'kalah'
    postgres=# CREATE DATABASE kalah
    
Run with maven:

    mvn test
    mvn spring-boot:run
    
### Use app
Location:

    localhost:8080
    
Login with:

    U: alexander
    P: alexander
    
or with:
    
    U: irene
    P: irene
    
    
