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
</form>
<form method = post>
    Surname : <input type = "text" name = "surname"/>
</form>
<form method = post>
    Phone Number : <input type="text" name= "phone_number"/>
</form>
<form method = post>
    Birthdate (YYYY-MM-DD) : <input type="text" name="birthdate"/>
</form>
<form method = post>
    Username : <input type="text" name="username"/>
</form>
<form method = post>
    Password : <input type="password" name="password"/>
</form>
<form method = post>
    <input type="submit" name="register" value="Register">
</form>
</body>
</html>
