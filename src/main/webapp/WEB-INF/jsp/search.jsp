<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 15.01.2021
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String [][] data = (String[][]) session.getAttribute("searchData");
%>
<html>
<head>
    <title>Search</title>
</head>
<body>
<form>
    <button type="button" onclick="location.href = '/user_menu';">Menu</button>
</form>
<form method="post">

    Title : <input type="text" name="title"/>
    Author : <input type="text" name="author"/>
    Genre : <input type="text" name="genre"/>
    Topic : <input type="text" name="topic"/>
    Currently Available : <input type="checkbox" name="available"/>
    Year Published : <input type="text" name="year_published"/>
    <input type="submit" name="searchBox" value="Search"/>

</form>

<form method="post">
    <p> Title : Author : Genre : Topic : Currently Available : Year Published </p>
    <%
        if (data != null){
            for(String [] item : data){
              %>
    <p> <%= item[0] %> : <%= item[1] %> : <%= item[2] %> : <%= item[3] %> : <%= item[4] %> : <%= item[5] %> : <%= item[6]%> : <button type="button" onclick="location.href = '/book?id=<%= item[0] %>';">More</button></p>
    <%
            }
        }

    %>
</form>

</body>
</html>
