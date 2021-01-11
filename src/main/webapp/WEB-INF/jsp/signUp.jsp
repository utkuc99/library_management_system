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

<form method = post>
    Name : <input type="text" name = "name"/>

    Surname : <input type = "text" name = "surname"/>

    Phone Number : <input type="text" name= "phone_number"/>

    Birthdate (YYYY-MM-DD) : <input type="text" name="birthdate"/>

    Username : <input type="text" name="username"/>

    Password : <input type="password" name="password"/>

    <input type="submit" name="register" value="Register">
</form>
</body>
</html>
