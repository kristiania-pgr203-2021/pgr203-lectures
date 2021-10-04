# Lecture 7: Introduction to JDBC

Goal: Learn how to install the database and connect with IntelliJ

## Plan

* [x] IntelliJ and PostgreSQL
* [x] Hello database
* [x] Test: Retrieve saved person
  * Add dependency assertj
* [x] Test: list people by last name


## Database:

1. Download and install PostgreSQL from https://www.postgresql.org/
2. Connect to the `postgres`-user with IntelliJ
3. Create a new database user and instance
   1. `create user person_dbuser with password '...'`
   2. `create database person_db with owner person_dbuser`
4. Connect to the new database with IntelliJ
5. Create and insert data into a table
   1. `create table people (id serial primary key, first_name varchar(50), last_name varchar(50))`
   2. `insert into people (first_name, last_name) values ('Test', 'Persson')`
   3. `insert into people (first_name, last_name) values ('Noen André', 'Persson')`
6. Query data from table
   1. `select * from people where first_name = 'Test'`
   2. `select * from people where last_name = 'Persson'`
   3. `select * from people where last_name ilike 'persso%'`
