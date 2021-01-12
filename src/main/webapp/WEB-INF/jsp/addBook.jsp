<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 12.01.2021
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Integer user_type = (Integer) session.getAttribute("userType"); %>
<html>
<head>
    <title>Add New Book</title>
</head>
<body>
<form method="post">
<%
    if (user_type == 3){
%>
    Title : <input type="text" name="title"/>
    Publication Date : <input type="text" name="publication_date"/>
    Author Name  : <input type="text" name="author_name"/>
    Publisher : <input type="text" name="publisher"/>
    Genre : <input type="text" name="genre"/>
    Topic : <input type="text" name="topics"/>
    <input type="submit" name="add" value="Add"/>
<%
    } else {
%>
    Title : <input type="text" name="title"/>
    Publication Date : <input type="text" name="publication_date"/>
    Author Name  : <input type="text" name="author_name"/>
    Publisher : <input type="text" name="publisher"/>
    Genre : <input type="text" name="genre"/>
    Topic : <input type="text" name="topics"/>
    <input type="submit" name="add" value="Add"/>
<%
    }
%>

</form>

</body>
</html>
