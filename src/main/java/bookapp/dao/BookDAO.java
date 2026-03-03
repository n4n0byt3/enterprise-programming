package bookapp.dao;

import bookapp.model.Book;
import bookapp.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAllBooks() throws Exception {
        String sql = "SELECT id, title, author, year_published, genre FROM books ORDER BY id";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(mapRow(rs));
            }
        }
        return books;
    }

    public int insertBook(Book book) throws Exception {
        String sql = "INSERT INTO books (title, author, year_published, genre) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYearPublished());
            ps.setString(4, book.getGenre());

            int affected = ps.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    public Book getBookById(int id) throws Exception {
        String sql = "SELECT id, title, author, year_published, genre FROM books WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public boolean updateBook(Book book) throws Exception {
        String sql = "UPDATE books SET title = ?, author = ?, year_published = ?, genre = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYearPublished());
            ps.setString(4, book.getGenre());
            ps.setInt(5, book.getId());

            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteBook(int id) throws Exception {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Simple search across title/author/genre (case-insensitive).
     * Keeps it flexible for both MVC and REST.
     */
    public List<Book> searchBooks(String q) throws Exception {
        String sql = """
            SELECT id, title, author, year_published, genre
            FROM books
            WHERE LOWER(title)  LIKE ?
               OR LOWER(author) LIKE ?
               OR LOWER(genre)  LIKE ?
            ORDER BY id
        """;

        String like = "%" + (q == null ? "" : q.trim().toLowerCase()) + "%";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(mapRow(rs));
                }
            }
        }
        return books;
    }

    private Book mapRow(ResultSet rs) throws Exception {
        return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("year_published"),
                rs.getString("genre")
        );
    }
}