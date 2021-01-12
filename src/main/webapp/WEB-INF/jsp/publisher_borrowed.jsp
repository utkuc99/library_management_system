<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 12.01.2021
  Time: 09:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Publisher's Borrowed Books</title>
</head>
<body>
<p> Title : Borrow Date : Username : Expected Return Date </p>
<%
    String[][] data = (String[][]) session.getAttribute("itemData");

    if (data != null)
    {
        for (String[] item : data)
        {
%>
<p> <%= item[0] %> : <%= item[1] %> : <%= item[2] %> : <%= item[3] %> </p>
<%
        }
    }
%>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
</body>
</html>
