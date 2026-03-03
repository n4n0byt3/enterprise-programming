<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bookapp.model.Book" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book List</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 18px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 10px; }
        th { background: #f3f3f3; text-align: left; }
    </style>
</head>
<body>

<h2>Books</h2>

<%
    List<?> raw = (List<?>) request.getAttribute("books");
    for (Object obj : raw) {
        Book b = (Book) obj;
%>
<tr>
    <td><%= b.getId() %></td>
    <td><%= b.getTitle() %></td>
    <td><%= b.getAuthor() %></td>
    <td><%= b.getYearPublished() %></td>
    <td><%= b.getGenre() %></td>
</tr>
<%
    }
%>

</body>
</html>