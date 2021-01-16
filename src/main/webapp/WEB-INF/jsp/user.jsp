<%--
  Created by IntelliJ IDEA.
  User: freedrone
  Date: 10.12.2020
  Time: 08:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.ozu.cs202project.classes.user" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>User Details Page</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<%
    user[] data = (user[]) session.getAttribute("itemData");
    Integer user_id = (Integer) session.getAttribute("userId");
    Integer user_type = (Integer) session.getAttribute("userType");

    if (data[0] != null) {
        for (user item : data)
        {
            if(user_type == 3){
%>
<p> <%= "username" %> : <%= item.user_name %>  </p>
<p> <%= "Name" %> : <%= item.name %>  </p>
<p> <%= "Surname" %> : <%= item.surname %>  </p>
<p> <%= "Phone Number" %> : <%= item.phone_number %>  </p>
<p> <%= "Birthdate" %> : <%= item.birthdate %>  </p>
<button type="button" onclick="location.href = '/borrow_hist_admin?id=<%= item.user_id %>';">Borrow History</button>
<%
            } else {
%>
<h3>You are not allowed to do this operation!</h3>
<%
            }
        }
    }
%>
</body>
</html>
