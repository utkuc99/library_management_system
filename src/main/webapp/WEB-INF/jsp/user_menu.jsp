<%--
  Created by IntelliJ IDEA.
  User: freedrone
  Date: 10.12.2020
  Time: 08:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.ozu.cs202project.classes.book" %>
<% Integer user_id = (Integer) session.getAttribute("userId"); %>
<html>
<head>
    <title>User Menu</title>
</head>
<body>
<p> <%= user_id %> </p>
<button type="button" onclick="location.href = '/list';">Available Books</button>
<button type="button" onclick="location.href = '/borrow_hist';">Borrow History</button>
</body>
</html>
