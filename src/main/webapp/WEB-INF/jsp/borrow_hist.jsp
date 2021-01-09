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
    <title>Borrow History</title>
</head>
<body>
<p> Title : Borrow Date : Expected Return Date </p>
<%
    String[][] data = (String[][]) session.getAttribute("itemData");

    if (data != null)
    {
        for (String[] item : data)
        {
%>
<p> <%= item[0] %> : <%= item[1] %> : <%= item[2] %> :
    <% if(item[3].equals("0")){ %>
    <button type="button" onclick="location.href = '/unborrow?id=<%= item[2] %>';">unBorrow</button>
    <%}%>
</p>
<%
        }
    }
%>
</body>
</html>
