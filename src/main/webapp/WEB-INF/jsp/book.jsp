<%--
  Created by IntelliJ IDEA.
  User: freedrone
  Date: 10.12.2020
  Time: 08:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.ozu.cs202project.classes.book" %>
<html>
<head>
    <title>Book Details Page</title>
</head>
<body>
<h3>Return To :</h3>
<button type="button" onclick="location.href = '/list';">Book List</button>
<button type="button" onclick="location.href = '/user_menu';">Menu</button>
<h3>Book Details:</h3>
<%
    book[] data = (book[]) session.getAttribute("bookData");
    Integer user_id = (Integer) session.getAttribute("userId");
    Integer user_type = (Integer) session.getAttribute("userType");

    if(data[0] == null){
%>
<p> This book in unavailable right now. </p>
<%
    }

    if (data[0] != null) {
        for (book item : data)
        {
%>
<p> <%= "Book ID" %> : <%= item.book_id %>  </p>
<p> <%= "Title" %> : <%= item.title %>  </p>
<p> <%= "Publication Date" %> : <%= item.publication_date %>  </p>
<p> <%= "Author Name" %> : <%= item.author_name %>  </p>
<p> <%= "Publisher" %> : <%= item.publisher %>  </p>
<p> <%= "Genre" %> : <%= item.genre %>  </p>
<p> <%= "Topics" %> : <%= item.topics %>  </p>
<p> <%= "Penalty for each late day" %> : <%= item.penalty %> ₺ </p>
<p> <%= "Is Borrowed" %> : <%= item.is_borrowed %>  </p>
<p> <%= "Is Held" %> : <%= item.is_held %>  </p>

<%
        if(item.is_borrowed == false && user_type == 1){
%>
<button type="button" onclick="location.href = '/borrow?id=<%= item.book_id %>';">Borrow</button>
<%
        }if((item.is_borrowed == true && item.is_held == false && user_type == 1) && user_type != 2){
%>
<button type="button" onclick="location.href = '/hold?id=<%= item.book_id %>';">Hold</button>
<%
        }if((item.is_held == true && item.held_user == user_id ) && user_type != 2){
%>
<button type="button" onclick="location.href = '/unhold?id=<%= item.book_id %>';">unHold</button>
<%
        }if(user_type == 3){
%>
<h3>Library Manager Only:</h3>
<p> <%= "Is Avaliable" %> : <%= item.is_avaliable %>  </p>
<p> <%= "Borrow Count" %> : <%= item.borrow_count %>  </p>
<p> <%= "Last Borrow" %> : <%= item.last_borrow %>  </p>
<p> <%= "Held User" %> : <%= item.held_user %>  </p>
<p> <%= "Penalty" %> : <%= item.penalty %>  </p>
<%
            if(item.is_borrowed == false){
%>
<form method = post>
    userID : <input type="text" name = "userID"/>
    <input type="submit" name="register" value="Register Borrow">
</form>
<%
            }
        }
        if(user_type == 2){
%>
<h3>Publisher Only:</h3>
<p> <%= "Is Avaliable" %> : <%= item.is_avaliable %>  </p>
<p> <%= "Borrow Count" %> : <%= item.borrow_count %>  </p>
<p> <%= "Last Borrow" %> : <%= item.last_borrow %>  </p>
<p> <%= "Penalty" %> : <%= item.penalty %>  </p>
<%
        }
        }
    }
%>
</body>
</html>