package bookapp.web.mvc;

import bookapp.dao.BookDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/books")
public class ListBooksServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;   // ← THIS MUST BE HERE

    private final BookDAO dao = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            req.setAttribute("books", dao.getAllBooks());
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/list.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Failed to load books", e);
        }
    }
}