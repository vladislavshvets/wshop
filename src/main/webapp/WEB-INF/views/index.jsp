<html>
<head>
    <title>Index</title>
</head>
<body>
<h1 style="color: green">Welcome to web-shop!</h1>
<header>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/registration">Registration</a></li>
            <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            <li><a href="${pageContext.request.contextPath}/users/all">All users</a></li>
            <li><a href="${pageContext.request.contextPath}/products">For admin: All products</a></li>
            <li><a href="${pageContext.request.contextPath}/products/user-view-all-products">For user: all products</a></li>
            <li><a href="${pageContext.request.contextPath}/shopping-carts/products">Shopping cart</a></li>
            <li><a href="${pageContext.request.contextPath}/orders/by-user">User orders</a></li>
            <li><a href="${pageContext.request.contextPath}/orders/admin">Admin orders</a></li>
            <li><a href="${pageContext.request.contextPath}/inject-data">Inject data to DB</a></li>
        </ul>
    </nav>
</header>
</body>
</html>
