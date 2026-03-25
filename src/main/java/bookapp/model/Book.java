package bookapp.model;
 
/**
 * Book model class representing a row in the books table.
 * Fields match the assignment SQL schema exactly:
 *   id, title, author, date, genres, characters, synopsis
 */
public class Book {
 
    private int    id;
    private String title;
    private String author;
    private String date;       // VARCHAR(10) e.g. "09/14/08"
    private String genres;     // VARCHAR(255) comma-separated genres
    private String characters; // TEXT  comma-separated character names
    private String synopsis;   // TEXT  book synopsis
 
    // ── Constructors ─────────────────────────────────────────────────────────
 
    public Book() {}
 
    /** Full constructor including id (used when reading from DB). */
    public Book(int id, String title, String author,
                String date, String genres, String characters, String synopsis) {
        this.id         = id;
        this.title      = title;
        this.author     = author;
        this.date       = date;
        this.genres     = genres;
        this.characters = characters;
        this.synopsis   = synopsis;
    }
 
    /** Constructor without id (used when inserting a new book). */
    public Book(String title, String author,
                String date, String genres, String characters, String synopsis) {
        this(0, title, author, date, genres, characters, synopsis);
    }
 
    // ── Getters & Setters ────────────────────────────────────────────────────
 
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
 
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
 
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
 
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
 
    public String getGenres() { return genres; }
    public void setGenres(String genres) { this.genres = genres; }
 
    public String getCharacters() { return characters; }
    public void setCharacters(String characters) { this.characters = characters; }
 
    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
}
 