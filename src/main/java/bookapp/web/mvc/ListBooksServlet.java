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
 * GET /books                      -> display all books (page 1)
 * GET /books?q=query              -> display books matching search term (page 1)
 * GET /books?page=N               -> display page N of all books
 * GET /books?q=query&page=N       -> display page N of search results
 *
 * Pagination is handled server-side:
 * the full result set is fetched from the DB, then sliced by page.
 */
@WebServlet("/books")
public class ListBooksServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 20;

    private final BookDAO dao = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("q");

        // Parse current page — default to 1
        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
                if (currentPage < 1) currentPage = 1;
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        try {
            List<Book> allBooks;

            if (query != null && !query.trim().isEmpty()) {
                // Search mode
                allBooks = dao.searchBooks(query.trim());
                request.setAttribute("searchQuery", query.trim());
            } else {
                // List all mode
                allBooks = dao.getAllBooks();
            }

            // ── Pagination calculations ──
            int totalBooks = allBooks.size();
            int totalPages = (int) Math.ceil((double) totalBooks / PAGE_SIZE);
            if (totalPages < 1) totalPages = 1;

            // Clamp currentPage to valid range
            if (currentPage > totalPages) currentPage = totalPages;

            int fromIndex = (currentPage - 1) * PAGE_SIZE;
            int toIndex   = Math.min(fromIndex + PAGE_SIZE, totalBooks);

            List<Book> pageBooks = allBooks.subList(fromIndex, toIndex);

            // ── Pass data to JSP ──
            request.setAttribute("books", pageBooks);
            request.setAttribute("totalBooks", totalBooks);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("pageSize", PAGE_SIZE);

            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Failed to load books", e);
        }
    }
}