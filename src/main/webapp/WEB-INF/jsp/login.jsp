<%--
  Created by IntelliJ IDEA.
  User: freedrone
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login to System</title>
</head>
<body>
<%
    String username = (String) session.getAttribute("username");

    if (username == null)
    {
%>
<p style="color: red" >${errorMessage}</p>
<form method="post">
    Username : <input type="text" name="username" />
    Password : <input type="password" name="password" />
    <input type="submit" value="Login" />
</form>
<%
    } else {
%>
<p>You are logged in as : <%= username %> </p>
<a href="/logout">Logout</a>
<%
    }
%>
</body>
</html>
