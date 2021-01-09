<%--
  Created by IntelliJ IDEA.
  User: freedrone
  Date: 10.12.2020
  Time: 08:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Integer user_id = (Integer) session.getAttribute("userId"); %>
<html>
<head>
    <title>Library Manager Menu</title>
</head>
<body>
<p> ÖzÜ Library Manager Menu </p>
<p> User : <%= user_id %> </p>
<button type="button" onclick="location.href = '/list';">Available Books</button>
<button type="button" onclick="location.href = '/borrow_hist';">Borrow History</button>
<button type="button" onclick="location.href = '/logout';">Logout</button>
</body>
</html>
