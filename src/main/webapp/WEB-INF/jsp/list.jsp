<%--
  Created by IntelliJ IDEA.
  User: freedrone
  Date: 10.12.2020
  Time: 08:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Items Page</title>
</head>
<body>
<% Integer user_type = (Integer) session.getAttribute("userType"); %>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<p> Title : Author : More Detail</p>
<%
    String[][] data = (String[][]) session.getAttribute("itemData");

    if (data != null)
    {
        for (String[] item : data)
        {
            if(user_type != 2)
            {
%>
<p> <%= item[0] %> : <%= item[1] %> : <button type="button" onclick="location.href = '/book?id=<%= item[2] %>';">More</button></p>

<%
            }
            else {
%>
 <p> <%= item[0] %> : <%= item[1] %></p>
<%
            }
        }
    }
%>
</body>
</html>
