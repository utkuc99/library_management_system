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
    <title>Number Of Times That Each Book Has Been Returned Late</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<%  Integer user_type = (Integer) session.getAttribute("userType");
    String[][] data = (String[][]) session.getAttribute("itemData");
    if(user_type == 3){
%>
<h2>Number Of Times That Each Book Has Been Returned Late</h2>
<h3> Book ID : Title : Borrow Count : Late Count : More Info</h3>
<%
    }
    if (data != null)
    {
        for (String[] item : data)
        {
            if(user_type == 3){
%>
<p> <%= item[0] %> : <%= item[1] %> : <%= item[2] %> : <%= item[3] %> : <button type="button" onclick="location.href = '/book?id=<%= item[0] %>';">More</button></p>
<%
            }
        }
    }
%>
</body>
</html>
