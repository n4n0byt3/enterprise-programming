package bookapp.web.mvc;

import bookapp.dao.BookDAO;
import bookapp.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class ListBooksServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final BookDAO dao = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String search = request.getParameter("search");

            int page = 1;
            int limit = 10;

            String pageParam = request.getParameter("page");

            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }

            int offset = (page - 1) * limit;

            List<Book> books;

            // If searching, ignore pagination for now
            if (search != null && !search.trim().isEmpty()) {

                books = dao.searchBooks(search);

                request.setAttribute("books", books);

                request.getRequestDispatcher("/WEB-INF/jsp/list.jsp")
                        .forward(request, response);

                return;
            }

            // Normal pagination
            books = dao.getBooksPage(offset, limit);

            int totalBooks = dao.countBooks();

            int totalPages = (int) Math.ceil((double) totalBooks / limit);

            request.setAttribute("books", books);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Failed to load books", e);
        }
    }
}