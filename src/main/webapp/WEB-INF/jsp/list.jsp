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
<p> Title : Author : More Detail</p>
<%
    String[][] data = (String[][]) session.getAttribute("itemData");

    if (data != null)
    {
        for (String[] item : data)
        {
            if(item[3].equals("1")){
%>
<p> <%= item[0] %> : <%= item[1] %> : <button type="button" onclick="location.href = '/book?id=<%= item[2] %>';">More</button></p>
<%
            }
        }
    }
%>
</body>
</html>
