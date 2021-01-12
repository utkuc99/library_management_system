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
    Request Type (1 for Addition, 0 for deletion): <input type="text" name="request_type"/>

    <input type="submit" name="request" value="Request"/>
</form>
<form method="post">
    <button type="button" onclick="location.href = '/user_menu';">Menu</button>
</form>

</body>
</html>
