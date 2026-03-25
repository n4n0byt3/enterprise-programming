package bookapp.web.mvc;

import bookapp.dao.BookDAO;
import bookapp.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller for listing and searching books.
 * Mapped to /books
 *
 * GET /books          -> display all books
 * GET /books?q=query  -> display books matching the search term
 */
@WebServlet("/books")
public class ListBooksServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookDAO dao = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("q");

        try {
            List<Book> books;

            if (query != null && !query.trim().isEmpty()) {
                // Search mode
                books = dao.searchBooks(query.trim());
                request.setAttribute("searchQuery", query.trim());
            } else {
                // List all mode
                books = dao.getAllBooks();
            }

            request.setAttribute("books", books);
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Failed to load books", e);
        }
    }
}