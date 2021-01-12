<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 7.01.2021
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign-Up</title>
</head>
<body>
<%
    String userID = (String) session.getAttribute("userID");
    String bookID = (String) session.getAttribute("bookID");

    if(userID == null || bookID == null){
%>
<p style="color: red">${error}</p>
<%
    }
%>

<form method = post>
    userID : <input type="text" name = "userID"/>

    bookID : <input type = "text" name = "bookID"/>

    <input type="submit" name="register" value="Register Borrow">
</form>
</body>
</html>
