<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 12.01.2021
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Integer user_type = (Integer) session.getAttribute("userType"); %>
<html>
<head>
    <title>Publisher Sign-up</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<h2>Publisher Sign-up</h2>
<%
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");
    String name = (String) session.getAttribute("name");
    String phone_number = (String) session.getAttribute("phone_number");

    if(username == null || password == null || name == null || phone_number == null){
%>
<p style="color: red">${error}</p>
<%
    } if(user_type == 3){
%>
<form method = post>
    Name : <input type="text" name = "name"/>
    </br></br>
    Phone Number : <input type="text" name= "phone_number"/>
    </br></br>
    Username : <input type="text" name="username"/>
    </br></br>
    Password : <input type="password" name="password"/>
    </br></br>
    <input type="submit" name="register" value="Register">
    <%
    } else {
    %>
    <h3>You are not allowed to do this operation!</h3>
<%
    }
%>
</form>
</body>
</html>
