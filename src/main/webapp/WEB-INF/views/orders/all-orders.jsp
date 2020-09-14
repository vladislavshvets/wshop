<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Admin: All user's orders </title>
</head>
<body>
<h1>All user's orders</h1>
<table border="1">
    <tr>
        <th>Order ID</th>
        <th>Products</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <c:out value="${order.products}"/>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">Back to main</a>
</body>
</html>
