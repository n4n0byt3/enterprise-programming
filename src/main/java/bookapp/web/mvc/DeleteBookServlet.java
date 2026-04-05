package bookapp.web.mvc;

import bookapp.dao.BookDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller for deleting a book.
 * Mapped to /delete-book
 *
 * GET /delete-book?id=X -> delete the book with that id, redirect to list
 *
 * Note: A GET is used here for simplicity in line with the MVC lab examples.
 * The delete is triggered by a link/button in list.jsp.
 */
@WebServlet("/delete-book")
public class DeleteBookServlet extends HttpServlet {

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
            dao.deleteBook(id);
        } catch (NumberFormatException e) {
            // Invalid id — just redirect without deleting
        } catch (Exception e) {
            throw new ServletException("Error deleting book", e);
        }

        response.sendRedirect("books");
    }
}