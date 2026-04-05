<%@ page contentType="text/html; charset=UTF-8" %>
<%
    // Redirect the root URL straight to the book list
    response.sendRedirect(request.getContextPath() + "/books");
%>
