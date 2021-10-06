# PostgreSQL, JDBC og Java

* [x] Installing postgresql
* [x] Accessing postgreql fra IntelliJ
* [ ] Test: retriving an inserted person from database
  * Add dependency on Postgreql driver with maven
  * Add AssertJ testing library with maven 
* [ ] Test: list people by last name


## Database

  create user person_dbuser with password '(redacted)';
  
  create database person_db with owner person_dbuser

  create table people (
    id serial primary key,
    first_name varchar(100),
    last_name varchar(100)
  );


