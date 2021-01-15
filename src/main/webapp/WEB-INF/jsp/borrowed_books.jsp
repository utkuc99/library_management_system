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
    <title>Borrowed Books</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<h3> Title : Borrow Date : Username : Expected Return Date : Penalty</h3>
<%
    String[][] data = (String[][]) session.getAttribute("itemData");

    if (data != null)
    {
        for (String[] item : data)
        {
%>
<p> <%= item[0] %> : <%= item[1] %> : <%= item[2] %> : <%= item[3] %> : <%= item[6] %>
    <% if(item[4].equals("0")){
    %>
     : <button type="button" onclick="location.href = '/returnbook?id=<%= item[5] %>';">Return Book</button>
    <%}%>
</p>
<%
        }
    }
%>
</body>
</html>
