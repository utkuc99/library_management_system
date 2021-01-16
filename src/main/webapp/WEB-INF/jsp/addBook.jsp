<%--
  Created by IntelliJ IDEA.
  User: Volkan
  Date: 12.01.2021
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Integer user_type = (Integer) session.getAttribute("userType"); %>
<% Integer user_id = (Integer) session.getAttribute("userId"); %>
<html>
<head>
    <title>Add New Book</title>
</head>
<body>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<h2>Add New Book</h2>
<form method="post">
<%
    if (user_type == 3){
%>
    Title : <input type="text" name="title"/>
    </br></br>
    Publication Date : <input type="text" name="publication_date"/>
    </br></br>
    Author Name  : <input type="text" name="author_name"/>
    </br></br>
    Publisher User ID: <input type="text" name="publisher"/>
    </br></br>
    Genre : <input type="text" name="genre"/>
    </br></br>
    Topic : <input type="text" name="topics"/>
    </br></br>
    <input type="submit" name="add" value="Add"/>
<%
    } if (user_type == 2){
%>
    Title : <input type="text" name="title"/>
    </br></br>
    Publication Date : <input type="text" name="publication_date"/>
    </br></br>
    Author Name  : <input type="text" name="author_name"/>
    </br></br>
    Genre : <input type="text" name="genre"/>
    </br></br>
    Topic : <input type="text" name="topics"/>
    </br></br>
    <input type="hidden" name="publisher" readonly="readonly"/>
    </br></br>
    <input type="submit" name="add" value="Add"/>
<%
    } if (user_type == 1){
%>
    <h3>You are not allowed to do this operation!</h3>
    <%
    }
%>

</form>

</body>
</html>
