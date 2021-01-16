<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 12.01.2021
  Time: 09:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Integer user_type = (Integer) session.getAttribute("userType"); %>
<html>
<head>
    <title>Publisher's Borrowed Books</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<h2>Borrowed Books</h2>
<%
    if(user_type == 2){

%>
<h3> Title : Borrow Date : Username : Expected Return Date </h3>
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
    } else {
%>
<h3>You are not allowed to do this operation!</h3>
<%
        }
%>
</body>
</html>
