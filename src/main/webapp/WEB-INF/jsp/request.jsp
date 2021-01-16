<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 11.01.2021
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Request</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<h2>Request Availability Change</h2>
<%
    String bookID = (String) session.getAttribute("bookID");
    String request_type = (String) session.getAttribute("request_type");
    String[][] data = (String[][]) session.getAttribute("itemData");
    try{
        Integer book_id = Integer.parseInt(bookID);
        Integer requestType = Integer.parseInt(request_type);

    } catch (NumberFormatException e){
%>
<p style="color: red">${error_id}</p>
<%
    }
%>
<form method="post">
    Book ID : <input type="text" name="bookID"/>
    </br></br>
    Request Type (0: Make Unavailable, 1: Make Available): <input type="text" name="request_type"/>
    </br></br>
    <input type="submit" name="request" value="Request"/>
</form>


</body>
</html>
