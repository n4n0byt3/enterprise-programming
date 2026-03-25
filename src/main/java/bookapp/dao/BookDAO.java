package bookapp.dao;

import bookapp.model.Book;
import bookapp.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the books table.
 * Implements full CRUD operations and search as required by the assignment.
 * Uses PreparedStatements throughout to prevent SQL injection.
 */
public class BookDAO {

    // ── READ ─────────────────────────────────────────────────────────────────

    /**
     * Returns all books ordered by id.
     */
    public List<Book> getAllBooks() throws Exception {
        String sql = "SELECT id, title, author, date, genres, characters, synopsis " +
                     "FROM books ORDER BY id";
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

    /**
     * Returns a single book by its primary key, or null if not found.
     */
    public Book getBookById(int id) throws Exception {
        String sql = "SELECT id, title, author, date, genres, characters, synopsis " +
                     "FROM books WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    /**
     * Searches books by title, genres, or date (case-insensitive LIKE match).
     * Supports the assignment requirement: search by title, year/date, and genre.
     */
    public List<Book> searchBooks(String q) throws Exception {
    	String sql = "SELECT id, title, author, date, genres, characters, synopsis " +
                "FROM books " +
                "WHERE LOWER(title)  LIKE ? " +
                "   OR LOWER(genres) LIKE ? " +
                "   OR LOWER(date)   LIKE ? " +
                "   OR LOWER(author) LIKE ? " +
                "ORDER BY id";

        String like = "%" + (q == null ? "" : q.trim().toLowerCase()) + "%";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(mapRow(rs));
                }
            }
        }
        return books;
    }

    // ── CREATE ────────────────────────────────────────────────────────────────

    /**
     * Inserts a new book and returns the generated primary key, or -1 on failure.
     */
    public int insertBook(Book book) throws Exception {
        String sql = "INSERT INTO books (title, author, date, genres, characters, synopsis) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getDate());
            ps.setString(4, book.getGenres());
            ps.setString(5, book.getCharacters());
            ps.setString(6, book.getSynopsis());

            int affected = ps.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────

    /**
     * Updates all fields of an existing book. Returns true if one row was changed.
     */
    public boolean updateBook(Book book) throws Exception {
        String sql = "UPDATE books SET title = ?, author = ?, date = ?, " +
                     "genres = ?, characters = ?, synopsis = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getDate());
            ps.setString(4, book.getGenres());
            ps.setString(5, book.getCharacters());
            ps.setString(6, book.getSynopsis());
            ps.setInt(7, book.getId());

            return ps.executeUpdate() == 1;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    /**
     * Deletes the book with the given id. Returns true if one row was removed.
     */
    public boolean deleteBook(int id) throws Exception {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    // ── PRIVATE HELPERS ───────────────────────────────────────────────────────

    /**
     * Maps a ResultSet row to a Book object.
     */
    private Book mapRow(ResultSet rs) throws Exception {
        return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("date"),
                rs.getString("genres"),
                rs.getString("characters"),
                rs.getString("synopsis")
        );
    }
}