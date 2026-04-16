package bookapp.web.mvc;

import bookapp.dao.BookDAO;
import bookapp.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Controller for editing an existing book.
 * Mapped to /edit-book
 *
 * GET  /edit-book?id=X -> load the book and show the edit form
 * POST /edit-book       -> validate input, update book, redirect to list
 */
@WebServlet("/edit-book")
public class EditBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Accepts DD/MM/YY or DD/MM/YYYY
    private static final Pattern DATE_PATTERN =
            Pattern.compile("^(0?[1-9]|[12]\\d|3[01])/(0?[1-9]|1[0-2])/(\\d{2}|\\d{4})$");

    private final BookDAO dao = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("books");
            return;
        }

        try {
            int id = Integer.parseInt(idParam.trim());
            Book book = dao.getBookById(id);

            if (book == null) {
                response.sendRedirect("books");
                return;
            }

            request.setAttribute("book", book);
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("books");
        } catch (Exception e) {
            throw new ServletException("Error loading book for edit", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String date = request.getParameter("date");
        String genres = request.getParameter("genres");
        String characters = request.getParameter("characters");
        String synopsis = request.getParameter("synopsis");

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("books");
            return;
        }

        String error = validate(title, author, date, genres);

        if (error != null) {
            Book book = new Book(id, title, author, date, genres, characters, synopsis);
            request.setAttribute("book",  book);
            request.setAttribute("error", error);
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp")
                   .forward(request, response);
            return;
        }

        try {
            Book book = new Book(id, title.trim(), author.trim(), date.trim(),
                                 genres.trim(), characters, synopsis);
            dao.updateBook(book);
            response.sendRedirect("books");
        } catch (Exception e) {
            throw new ServletException("Error updating book in database", e);
        }
    }

    /**
     * Validates all required fields including date format.
     * Returns an error message string, or null if all inputs are valid.
     */
    private String validate(String title, String author, String date, String genres) {
        if (title == null || title.trim().isEmpty())
            return "Title is required.";
        if (title.trim().length() > 255)
            return "Title must be 255 characters or fewer.";
        if (author == null || author.trim().isEmpty())
            return "Author is required.";
        if (author.trim().length() > 255)
            return "Author must be 255 characters or fewer.";
        if (date == null || date.trim().isEmpty())
            return "Date is required.";
        if (!DATE_PATTERN.matcher(date.trim()).matches())
            return "Date must be in DD/MM/YY or DD/MM/YYYY format (e.g. 14/09/2008).";
        if (genres == null || genres.trim().isEmpty())
            return "At least one genre is required.";
        return null;
    }
}