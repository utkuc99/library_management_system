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
<%if (user_type == 3){%>
<p> ÖzÜ Library Manager Statistics Menu </p>
<p> User : <%= user_id %> </p>
<button type="button" onclick="location.href = '/stats_most_genres';">Most Borrowed Genres</button>
<button type="button" onclick="location.href = '/stats_most_books';">Most Borrowed Books (Last 3 Months)</button>
<button type="button" onclick="location.href = '/stats_most_publisher';">Publisher's With Most Borrowed Books</button>
<button type="button" onclick="location.href = '/overdue_book_number';">Overdue Book Number</button>
<button type="button" onclick="location.href = '/borrowers_most_book';">Borrowers of Most Borrowed Book</button>
<button type="button" onclick="location.href = '/stats_overdue_number';">Number Of Times That Each Book Has Been Returned Late</button>
<% }%>

</body>
</html>
