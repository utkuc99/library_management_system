<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 16.01.2021
  Time: 00:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String [][] data = (String[][]) session.getAttribute("itemData");
%>
<html>
<head>
    <title>Publisher Search</title>
</head>
<body>
<form method="post">
    <button type="button" onclick="location.href = '/user_menu';">Menu</button>
</form>
<form method="post">
    Publisher : <input type="text" name="publisher"/>
    Genre : <input type="text" name="genre"/>
    <input type="submit" name="search" value="Search"/>
</form>
<form method="post">
<p> Publisher : Genre </p>
<%
    if (data != null){
        for(String [] item : data){
%>
<p> <%= item[0] %> : <%= item[1] %> </p>
<%
        }
    }

%>
</form>

</body>
</html>
