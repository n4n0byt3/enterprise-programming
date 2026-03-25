package bookapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a JDBC connection to the mudfoot MySQL database.
 * The assignment specifies using your mudfoot account for the books table.
 *
 * IMPORTANT: Update USER and PASSWORD to your own mudfoot credentials.
 */
public class DBConnection {

    // ── Update these to match your mudfoot account ────────────────────────────
    private static final String URL =
            "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/chopracs" +
            "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String USER     = "chopracs";
    private static final String PASSWORD = "LowdEwfos2";
    // ─────────────────────────────────────────────────────────────────────────

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found on classpath", e);
        }
    }

    /**
     * Opens and returns a new connection to the database.
     * Callers are responsible for closing it (use try-with-resources).
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}