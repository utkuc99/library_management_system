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
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<% Integer user_type = (Integer) session.getAttribute("userType");
    if(user_type == 1){
%>
<h3> ID : Title : Author : More Detail</h3>
<%
    }
    if(user_type > 1){
%>
<h3> ID : Title : Author : Availability : More Detail</h3>
<%
    }
    String[][] data = (String[][]) session.getAttribute("itemData");

    if (data != null)
    {
        for (String[] item : data)
        {
            if(user_type == 1){
%>
<p> <%= item[2] %> : <%= item[0] %> : <%= item[1] %> : <button type="button" onclick="location.href = '/book?id=<%= item[2] %>';">More</button></p>
<%
            }
            if(user_type > 1){
%>
<p> <%= item[3] %> : <%= item[0] %> : <%= item[1] %> : <%= item[2] %> : <button type="button" onclick="location.href = '/book?id=<%= item[3] %>';">More</button></p>
<%
            }
        }

    }
%>
</body>
</html>
