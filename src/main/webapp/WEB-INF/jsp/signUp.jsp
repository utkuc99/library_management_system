<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 7.01.2021
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign-Up</title>
</head>
<body>
<button type="button" onclick="location.href = '/login';">Login Screen</button>
<h2>Sign-up</h2>
<%
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");
    String name = (String) session.getAttribute("name");
    String phone_number = (String) session.getAttribute("phone_number");
    String surname = (String) session.getAttribute("surname");
    String birthdate = (String) session.getAttribute("birthdate");

    if(username == null || password == null || name == null || phone_number == null || surname == null || birthdate == null){
%>
<p style="color: red">${error}</p>
<%
    }
%>

<form method = post>
    Name : <input type="text" name = "name"/>
    </br></br>
    Surname : <input type = "text" name = "surname"/>
    </br></br>
    Phone Number : <input type="text" name= "phone_number"/>
    </br></br>
    Birthdate (YYYY-MM-DD) : <input type="text" name="birthdate"/>
    </br></br>
    Username : <input type="text" name="username"/>
    </br></br>
    Password : <input type="password" name="password"/>
    </br></br>
    <input type="submit" name="register" value="Register">
</form>
</body>
</html>
