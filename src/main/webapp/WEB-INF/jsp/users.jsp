<%--
  Created by IntelliJ IDEA.
  User: freedrone
  Date: 10.12.2020
  Time: 08:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Integer user_type = (Integer) session.getAttribute("userType"); %>
<html>
<head>
    <title>Borrow History</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<%
    if(user_type == 3){

%>
<h3> Username : Name : Surname : More Detail </h3>
<%
    String[][] data = (String[][]) session.getAttribute("itemData");

    if (data != null)
    {
        for (String[] item : data)
        {
%>
<p> <%= item[0] %> : <%= item[1] %> : <%= item[2] %> : <button type="button" onclick="location.href = '/user?id=<%= item[3] %>';">More</button>
</p>
<%
        }
    }
} else {
%>
<h3>You are not allowed to do this operation!</h3>
<%
    }
%>
</body>
</html>
