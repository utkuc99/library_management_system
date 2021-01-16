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
    <title>Number Of Times That Each Book Has Been Returned Late</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<%  Integer user_type = (Integer) session.getAttribute("userType");
    String[][] data = (String[][]) session.getAttribute("itemData");
%>
<h2>User Statistics</h2>
<%
    if (data != null)
    {
%>
<p> Number of books that are overdue: <%= data[0][0] %> </p>
<p> Number of books that were overdue: <%= data[1][0] %> </p>
<p> Number of books that are booked right now: <%= data[2][0] %> </p>
<p> Favorite Genre: <%= data[3][0] %> Number of bookings: <%= data[3][1] %> </p>
<%

    }
%>
</body>
</html>
