<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1> Hello! Please provide your user details! </h1>
<h4 style="color:red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/registration">
    Your name: <input type="text" name="name">
    Login: <input type="text" name="login">
    Password: <input type="password" name="pwd">
    Repeat your password: <input type="password" name="pwd-repeat">
    <button type="submit">Submit!</button>
</form>
<a href="${pageContext.request.contextPath}/">Back to main</a>
</body>
</html>
