<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 14.01.2021
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Requests</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<h3> Request ID : Request Date : Requester ID : Book ID : Request Type : Result : Result Date : Result User </h3>
<%
    String[][] data = (String[][]) session.getAttribute("itemData");

    if (data != null)
    {
        for (String[] item : data)
        {
%>
<p> <%= item[0] %> : <%= item[1] %> : <%= item[2] %> : <%= item[3] %> : <%= item[4] %> : <%= item[5] %> :<%= item[6] %> : <%= item[7] %>
    <%
        if(item[5] == null){
    %>
    <form method="post">
    <input type="hidden" name="requestId" value="<%= item[0] %>"/>
    <input type="hidden" name="bookId" value="<%= item[3] %>"/>
    <input type="hidden" name="request_type" value="<%= item[4] %>">
    <input type="submit" name="accept" value="Accept"/> <input type="submit" name="reject" value="Reject"/>
    </form>

    <%
        }
    %>
</p>
<%
        }
    }
%>

</body>
</html>
