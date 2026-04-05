# Enterprise Programming – Book Management System

## Technologies
- Java
- JSP / Servlets
- MySQL
- JDBC
- Apache Tomcat

## Features
- View all books
- Search books
- Pagination
- Add new book
- Edit book
- Delete book

## Setup Instructions

### 1. Install Requirements
- Java 17+
- Apache Tomcat 10
- MySQL 8

### 2. Create Database

Run:

resources/database/schema.sql

### 3. Configure Database

Edit:

src/main/resources/db.properties

Update:

db.username  
db.password

### 4. Run Application

Start Tomcat in Eclipse.

Open:

http://localhost:8081/enterprise-programming-bookapp/books

## Architecture

Model:
Book.java

DAO:
BookDAO.java

Controllers:
ListBooksServlet  
AddBookServlet  
EditBookServlet  
DeleteBookServlet  
ViewBookServlet  

Views:
list.jsp  
add.jsp  
edit.jsp  
view.jsp