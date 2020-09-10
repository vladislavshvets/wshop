<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>
<body>
<h1>List your products: </h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Product</th>
        <td>Price</td>
        <td>Options</td>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr><td>
            <c:out value="${product.id}"/>
        </td>
            <td>
            <c:out value="${product.name}"/>
            </td>
            <td>
            <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/shopping-cart/products/add?id=${product.id}">Buy</a>
            </td>
            </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/products/add">Add product</a>
<a href="${pageContext.request.contextPath}/">Back to main</a>
</body>
</html>
