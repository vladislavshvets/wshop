# Internet Shop project

*For practice back-end skills. This project is designed according to the SOLID principles n-tier architecture 
and consist of the next layers: Dao, Service, and Web controllers.*

## General description
- Authentication and authorization users and hashing SHA-512 passwords
 and adding salt.
- CRUD operation with models: user, product, shoppingCart, order.
- Web views by Role-based access control, in this project we have Admin and User role.
##### ADMIN:
Can view all orders and shopping carts;
+ Can add / delete products from the database;
+ Can create / delete orders; 
+ Can create / delete shopping cart;
+ Can delete users;
+ Have all functional of USER
##### USER:
+ Can add a product to the shopping cart;
+ Can place an order;
+ Can view their orders.  
- MySQL DB for saving users and orders history. 

## This project used technologies such as: 
+ Java Core + 8
+ JDBC
+ Maven
+ Tomcat
+ Servlets
+ JSP
+ MySQL
+ OOP, SOLID, RBAC principles.  

## Guide how to start working with this project)
- Download and install the JDK
- Download and install servlet container (I recommend you to chose TomCat)
- Download and install MySQL Server and MySQL Workbench
- Setup connection with MySQL using default parameters
- user: "root"
- password: "1234"
- url: jdbc:mysql://localhost:3306/internet_shop?serverTimezone=UTC
- Create a schema with name "internet_shop"
- Copy SQL script from init_db.sql in src/main/resources/ folder and run it in MySQL Workbench
- You can't register as admin, so you need to create admin in MySQL Workbench and then you can use this login and pass while logging in
to get admin access.
- After that you can run this app and use button "inject data" orr log in as admin and add your own data.
