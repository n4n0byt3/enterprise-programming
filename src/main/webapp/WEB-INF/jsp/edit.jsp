<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="bookapp.model.Book" %>
<%
    Book book = (Book) request.getAttribute("book");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Book</title>
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
            max-width: 680px;
            margin: 36px auto;
            padding: 0 20px;
        }

        .card {
            background: #fff;
            border-radius: 6px;
            padding: 32px 36px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        }

        h2 { font-size: 1.3rem; margin-bottom: 24px; }

        .error-box {
            background: #faeaea;
            border-left: 4px solid #a63030;
            color: #a63030;
            padding: 10px 14px;
            border-radius: 4px;
            margin-bottom: 20px;
            font-family: Arial, sans-serif;
            font-size: 0.9rem;
        }

        .form-group { margin-bottom: 18px; }

        label {
            display: block;
            font-size: 0.88rem;
            font-family: Arial, sans-serif;
            font-weight: bold;
            color: #5a4a32;
            margin-bottom: 5px;
            text-transform: uppercase;
            letter-spacing: 0.4px;
        }

        label .required { color: #a63030; }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 9px 12px;
            border: 1px solid #c8bca8;
            border-radius: 4px;
            font-size: 0.95rem;
            font-family: 'Georgia', serif;
            background: #fdfaf5;
            transition: border-color 0.2s;
        }

        input[type="text"]:focus,
        textarea:focus {
            outline: none;
            border-color: #c9872a;
        }

        textarea { resize: vertical; min-height: 90px; }

        .hint {
            font-size: 0.8rem;
            color: #9a8a70;
            margin-top: 3px;
            font-family: Arial, sans-serif;
        }

        .form-actions {
            display: flex;
            gap: 12px;
            margin-top: 28px;
        }

        .btn-submit {
            background: #4a7c2a;
            color: #fff;
            padding: 10px 28px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.95rem;
            font-family: Arial, sans-serif;
        }

        .btn-submit:hover { background: #3a6020; }

        .btn-cancel {
            color: #7a6a52;
            text-decoration: none;
            padding: 10px 0;
            font-size: 0.9rem;
            font-family: Arial, sans-serif;
            align-self: center;
        }
    </style>
</head>
<body>

<header>
    <h1>&#128218; Book Library</h1>
</header>

<main>
    <div class="card">
        <h2>Edit Book &mdash; ID <%= book.getId() %></h2>

        <%-- Server-side error display --%>
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-box"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="edit-book" method="post" onsubmit="return validateForm()">

            <input type="hidden" name="id" value="<%= book.getId() %>">

            <div class="form-group">
                <label>Title <span class="required">*</span></label>
                <input type="text" name="title" maxlength="255" required
                       value="<%= book.getTitle() != null ? book.getTitle() : "" %>">
            </div>

            <div class="form-group">
                <label>Author <span class="required">*</span></label>
                <input type="text" name="author" maxlength="255" required
                       value="<%= book.getAuthor() != null ? book.getAuthor() : "" %>">
            </div>

            <div class="form-group">
                <label>Date <span class="required">*</span></label>
                <input type="text" name="date" maxlength="10" required
                       placeholder="e.g. 14/09/2008"
                       value="<%= book.getDate() != null ? book.getDate() : "" %>">
                <p class="hint">Format: DD/MM/YY or DD/MM/YYYY</p>
            </div>

            <div class="form-group">
                <label>Genres <span class="required">*</span></label>
                <input type="text" name="genres" maxlength="255" required
                       value="<%= book.getGenres() != null ? book.getGenres() : "" %>">
            </div>

            <div class="form-group">
                <label>Characters</label>
                <textarea name="characters"><%= book.getCharacters() != null ? book.getCharacters() : "" %></textarea>
            </div>

            <div class="form-group">
                <label>Synopsis</label>
                <textarea name="synopsis" style="min-height:120px;"><%= book.getSynopsis() != null ? book.getSynopsis() : "" %></textarea>
            </div>

            <div class="form-actions">
                <button class="btn-submit" type="submit">Save Changes</button>
                <a class="btn-cancel" href="books">Cancel</a>
            </div>

        </form>
    </div>
</main>

<script>
    // Client-side validation — mirrors server-side rules in EditBookServlet.java
    // Accepts DD/MM/YY or DD/MM/YYYY
    var DATE_RE = /^(0?[1-9]|[12]\d|3[01])\/(0?[1-9]|1[0-2])\/(\d{2}|\d{4})$/;

    function validateForm() {
        var title  = document.querySelector('[name="title"]').value.trim();
        var author = document.querySelector('[name="author"]').value.trim();
        var date   = document.querySelector('[name="date"]').value.trim();
        var genres = document.querySelector('[name="genres"]').value.trim();

        if (!title)               { alert("Title is required.");   return false; }
        if (!author)              { alert("Author is required.");  return false; }
        if (!date)                { alert("Date is required.");    return false; }
        if (!DATE_RE.test(date))  { alert("Date must be DD/MM/YY or DD/MM/YYYY (e.g. 14/09/2008)."); return false; }
        if (!genres)              { alert("Genres are required."); return false; }

        return true;
    }
</script>

</body>
</html>
