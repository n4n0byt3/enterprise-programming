package bookapp.web.mvc;

import bookapp.dao.BookDAO;
import bookapp.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller for viewing a single book's full details.
 * Mapped to /book
 *
 * GET /book?id=X -> load the book and display view.jsp
 */
@WebServlet("/book")
public class ViewBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
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
            request.getRequestDispatcher("/WEB-INF/jsp/view.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("books");
        } catch (Exception e) {
            throw new ServletException("Error loading book details", e);
        }
    }
}