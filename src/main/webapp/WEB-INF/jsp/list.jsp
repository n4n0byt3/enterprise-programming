<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bookapp.model.Book" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Library</title>
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
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        header h1 { font-size: 1.6rem; letter-spacing: 1px; }

        .add-btn {
            background: #c9872a;
            color: #fff;
            padding: 8px 20px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 0.9rem;
            font-family: Arial, sans-serif;
        }

        .add-btn:hover { background: #a86e1e; }

        main { padding: 28px 32px; }

        /* Search bar */
        .search-form {
            display: flex;
            gap: 8px;
            margin-bottom: 24px;
        }

        .search-form input[type="text"] {
            flex: 1;
            padding: 9px 14px;
            border: 1px solid #c8bca8;
            border-radius: 4px;
            font-size: 0.95rem;
            background: #fff;
        }

        .search-form button {
            padding: 9px 22px;
            background: #2c2416;
            color: #f5f0e8;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.95rem;
        }

        .search-form button:hover { background: #4a3d2a; }

        .clear-link {
            padding: 9px 14px;
            color: #7a6a52;
            text-decoration: none;
            font-size: 0.9rem;
            align-self: center;
        }

        .result-info {
            font-size: 0.88rem;
            color: #7a6a52;
            margin-bottom: 14px;
            font-family: Arial, sans-serif;
        }

        /* Table */
        table {
            width: 100%;
            border-collapse: collapse;
            background: #fff;
            border-radius: 6px;
            overflow: hidden;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        }

        thead { background: #2c2416; color: #f5f0e8; }

        th {
            padding: 12px 14px;
            text-align: left;
            font-size: 0.85rem;
            font-family: Arial, sans-serif;
            letter-spacing: 0.5px;
            text-transform: uppercase;
        }

        td {
            padding: 11px 14px;
            font-size: 0.93rem;
            border-bottom: 1px solid #ede7da;
            vertical-align: top;
        }

        tr:last-child td { border-bottom: none; }
        tr:hover td { background: #faf6ef; }

        .genre-cell {
            max-width: 180px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            font-size: 0.85rem;
            color: #6a5a40;
        }

        /* Action links */
        .actions { white-space: nowrap; }

        .actions a {
            font-size: 0.82rem;
            font-family: Arial, sans-serif;
            text-decoration: none;
            padding: 3px 9px;
            border-radius: 3px;
            margin-right: 4px;
            display: inline-block;
        }

        .btn-view   { color: #1a5c8a; background: #e8f2fa; }
        .btn-edit   { color: #4a7c2a; background: #edf6e5; }
        .btn-delete { color: #a63030; background: #faeaea; }

        .btn-view:hover   { background: #cce3f5; }
        .btn-edit:hover   { background: #d9f0c8; }
        .btn-delete:hover { background: #f5d2d2; }

        .no-results {
            text-align: center;
            padding: 40px;
            color: #9a8a70;
            font-style: italic;
        }
    </style>
</head>
<body>

<header>
    <h1>&#128218; Book Library</h1>
    <a class="add-btn" href="add-book">+ Add Book</a>
</header>

<main>

    <%-- Search form --%>
    <form class="search-form" action="books" method="get">
        <input type="text"
               name="q"
               placeholder="Search by title, author, genre, or date..."
               value="<%= request.getAttribute("searchQuery") != null
                          ? request.getAttribute("searchQuery") : "" %>">
        <button type="submit">Search</button>
        <% if (request.getAttribute("searchQuery") != null) { %>
            <a class="clear-link" href="books">&#x2715; Clear</a>
        <% } %>
    </form>

    <%
        List<Book> books = (List<Book>) request.getAttribute("books");
        String sq = (String) request.getAttribute("searchQuery");
    %>

    <% if (sq != null) { %>
        <p class="result-info">
            <%= books.size() %> result<%= books.size() != 1 ? "s" : "" %>
            for &ldquo;<%= sq %>&rdquo;
        </p>
    <% } %>

    <% if (books == null || books.isEmpty()) { %>
        <p class="no-results">No books found.</p>
    <% } else { %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Date</th>
                    <th>Genres</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <% for (Book b : books) { %>
                <tr>
                    <td><%= b.getId() %></td>
                    <td><%= b.getTitle() %></td>
                    <td><%= b.getAuthor() %></td>
                    <td><%= b.getDate() %></td>
                    <td class="genre-cell" title="<%= b.getGenres() %>"><%= b.getGenres() %></td>
                    <td class="actions">
                        <a class="btn-view"   href="book?id=<%= b.getId() %>">View</a>
                        <a class="btn-edit"   href="edit-book?id=<%= b.getId() %>">Edit</a>
                        <a class="btn-delete"
                           href="delete-book?id=<%= b.getId() %>"
                           onclick="return confirm('Delete \'<%= b.getTitle().replace("'", "\\'") %>\'?')">Delete</a>
                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>
    <% } %>

</main>

</body>
</html>
