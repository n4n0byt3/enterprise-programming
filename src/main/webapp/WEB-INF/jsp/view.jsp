<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="bookapp.model.Book" %>
<%
    Book book = (Book) request.getAttribute("book");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= book.getTitle() %></title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: 'Georgia', serif;
            background: #f5f0e8;
            color: #2c2416;
            min-height: 100vh;
        }

        header {
            background: #2c2416;
            color: #f5f0e8;
            padding: 20px 32px;
        }

        header h1 { font-size: 1.4rem; }

        main {
            max-width: 720px;
            margin: 36px auto;
            padding: 0 20px;
        }

        .card {
            background: #fff;
            border-radius: 6px;
            padding: 36px 40px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        }

        .book-title {
            font-size: 1.7rem;
            margin-bottom: 4px;
        }

        .book-author {
            font-size: 1rem;
            color: #7a6a52;
            margin-bottom: 24px;
            font-style: italic;
        }

        .divider {
            border: none;
            border-top: 1px solid #ede7da;
            margin: 20px 0;
        }

        .field { margin-bottom: 16px; }

        .field-label {
            font-size: 0.78rem;
            font-family: Arial, sans-serif;
            font-weight: bold;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            color: #9a8a70;
            margin-bottom: 4px;
        }

        .field-value {
            font-size: 0.97rem;
            line-height: 1.6;
        }

        .synopsis {
            background: #fdfaf5;
            border-left: 3px solid #c9872a;
            padding: 14px 18px;
            border-radius: 0 4px 4px 0;
            font-size: 0.93rem;
            line-height: 1.7;
            color: #3a2e1e;
        }

        .actions {
            display: flex;
            gap: 12px;
            margin-top: 32px;
        }

        .btn {
            padding: 9px 22px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 0.9rem;
            font-family: Arial, sans-serif;
        }

        .btn-edit { background: #edf6e5; color: #4a7c2a; }
        .btn-delete { background: #faeaea; color: #a63030; }
        .btn-back { background: #f0ebe0; color: #5a4a32; }

        .btn-edit:hover { background: #d9f0c8; }
        .btn-delete:hover { background: #f5d2d2; }
        .btn-back:hover { background: #e5dfd0; }
    </style>
</head>
<body>

<header>
    <h1>&#128218; Book Library</h1>
</header>

<main>
    <div class="card">

        <h2 class="book-title"><%= book.getTitle() %></h2>
        <p class="book-author">by <%= book.getAuthor() %></p>

        <hr class="divider">

        <div class="field">
            <div class="field-label">Book ID</div>
            <div class="field-value">#<%= book.getId() %></div>
        </div>

        <div class="field">
            <div class="field-label">Date Published</div>
            <div class="field-value"><%= book.getDate() != null ? book.getDate() : "—" %></div>
        </div>

        <div class="field">
            <div class="field-label">Genres</div>
            <div class="field-value"><%= book.getGenres() != null ? book.getGenres() : "—" %></div>
        </div>

        <% if (book.getCharacters() != null && !book.getCharacters().trim().isEmpty()) { %>
        <div class="field">
            <div class="field-label">Characters</div>
            <div class="field-value"><%= book.getCharacters() %></div>
        </div>
        <% } %>

        <% if (book.getSynopsis() != null && !book.getSynopsis().trim().isEmpty()) { %>
        <hr class="divider">
        <div class="field">
            <div class="field-label">Synopsis</div>
            <div class="synopsis"><%= book.getSynopsis() %></div>
        </div>
        <% } %>

        <div class="actions">
            <a class="btn btn-edit" href="edit-book?id=<%= book.getId() %>">Edit</a>
            <a class="btn btn-delete"
               href="delete-book?id=<%= book.getId() %>"
               onclick="return confirm('Delete this book?')">Delete</a>
            <a class="btn btn-back" href="books">Back to List</a>
        </div>

    </div>
</main>

</body>
</html>
