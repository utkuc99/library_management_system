<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 12.01.2021
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Integer user_type = (Integer) session.getAttribute("userType"); %>
<% Integer user_id = (Integer) session.getAttribute("userId"); %>
<html>
<head>
    <title>Disable Book</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<h2>Disable Book</h2>
<form method="post">
<%
    if (user_type == 3){
%>
    bookId : <input type="text" name="bookid"/>
    </br></br>
    <input type="submit" name="add" value="Disable"/>
<%
    } if (user_type == 2){
%>
    <h3>You are not allowed to do this operation!</h3>
<%
    } if (user_type == 1){
%>
    <h3>You are not allowed to do this operation!</h3>
    <%
    }
%>

</form>

</body>
</html>
