<%--
  Created by IntelliJ IDEA.
  User: freedrone
  Date: 10.12.2020
  Time: 08:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Integer user_id = (Integer) session.getAttribute("userId"); %>
<% Integer user_type = (Integer) session.getAttribute("userType"); %>
<html>
<head>
    <title>User Menu</title>
</head>
<body>

<% if (user_type == 1){%>
<p> ÖzÜ Library User Menu </p>
<p> User : <%= user_id %> </p>
<button type="button" onclick="location.href = '/list';">Available Books</button>
<button type="button" onclick="location.href = '/borrow_hist';">Borrow History</button>
<button type="button" onclick="location.href = '/logout';">Logout</button>
<% }if (user_type == 2){%>
<p> ÖzÜ Library Publisher Menu </p>
<p> User : <%= user_id %> </p>
<button type="button" onclick="location.href = '/list';">Available Books</button>
<button type="button" onclick="location.href = '/publisher_borrowed';">Borrowed Books</button>
<button type="button" onclick="location.href = '/request';">Request</button>
<button type="button" onclick="location.href = '/logout';">Logout</button>
<% }if (user_type == 3){%>
<p> ÖzÜ Library Manager Menu </p>
<p> User : <%= user_id %> </p>
<button type="button" onclick="location.href = '/list';">Books</button>
<button type="button" onclick="location.href = '/borrowed_books';">Borrowed Books</button>
<button type="button" onclick="location.href = '/users';">Users</button>
<button type="button" onclick="location.href = '/publisher_signup';">Publisher Sign-up</button>
<button type="button" onclick="location.href = '/addBook';">Add New Book</button>
<button type="button" onclick="location.href = '/requests';">Requests</button>
<button type="button" onclick="location.href = '/logout';">Logout</button>
<% }%>

</body>
</html>
