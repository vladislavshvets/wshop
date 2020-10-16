##Internet Shop pet-project

*For practice back-end skills. This project is designed according to the SOLID principles n-tier architecture 
and consist of the next layers: Dao, Service, and Web controllers.*

##General description
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
