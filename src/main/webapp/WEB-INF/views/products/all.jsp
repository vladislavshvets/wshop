<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Administration view. All products: </title>
</head>
<body>
<h1> Administration view. All products: </h1>
<table border="1">
    <tr>
        <th>ID</th>
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
                <a href="${pageContext.request.contextPath}/products/delete?id=${product.id}">Delete</a>
            </td>
            </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/products/add">Add new product</a>
<a href="${pageContext.request.contextPath}/">Back to main</a>
</body>
</html>
