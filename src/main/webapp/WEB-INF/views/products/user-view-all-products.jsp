<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products: </title>
</head>
<body>
<h1>List your products: </h1>
<table border="1">
    <tr>
        <td>ID</td>
        <th>Product</th>
        <td>Price</td>
        <td>Options</td>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/shopping-carts/products/add?id=${product.id}">Add! </a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/"> Back to main</a>
<a href="${pageContext.request.contextPath}/shopping-carts/products"> Go to shopping cart! </a>
</body>
</html>
