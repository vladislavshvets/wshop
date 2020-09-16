<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inject data.</title>
</head>
<body>
<h1> Data was added from DB </h1>
<a href="/"${pageContext.request.contextPath}/"> Go to the main page </a>
<a href="${pageContext.request.contextPath}/login">Go to login</a>
</body>
</html>
