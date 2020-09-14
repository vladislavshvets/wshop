<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New product</title>
</head>
<body>
<h1 style="color: greenyellow">${message}</h1>
<form method="post" action="${pageContext.request.contextPath}/products/add">
    Product name <input type="text" name="name">
    Price <input type="text" name="price">
    <button type="submit">Add new product!</button>
</form>
<a href="${pageContext.request.contextPath}/products">Back to list all products</a>
</body>
</html>
