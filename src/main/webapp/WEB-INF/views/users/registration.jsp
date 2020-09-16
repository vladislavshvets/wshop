<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1> Hello! Please provide your user details! </h1>
<h4 style="color:red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/registration">
    Your name: <input type="text" name="name"> <br>
    Login: <input type="text" name="login"><br>
    Password: <input type="password" name="pwd"><br>
    Repeat your password: <input type="password" name="pwd-repeat">
    <button type="submit">Submit!</button>
</form>
<a href="${pageContext.request.contextPath}/">Back to main</a>
</body>
</html>
