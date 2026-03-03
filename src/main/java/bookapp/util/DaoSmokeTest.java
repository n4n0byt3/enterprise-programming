package bookapp.util;

import bookapp.model.Book;

import java.util.List;

import bookapp.dao.*;

public class DaoSmokeTest {
	 public static void main(String[] args) {
	        try {
	            BookDAO dao = new BookDAO();

	            System.out.println("---- Initial books ----");
	            print(dao.getAllBooks());

	            // INSERT
	            Book created = new Book("DAO CRUD Test", "Shivam", 2026, "Test");
	            int newId = dao.insertBook(created);
	            System.out.println("\nInserted new book id = " + newId);

	            // GET BY ID
	            Book fetched = dao.getBookById(newId);
	            System.out.println("\nFetched by id:");
	            System.out.println(fetched.getId() + " | " + fetched.getTitle() + " | " + fetched.getAuthor());

	            // UPDATE
	            fetched.setTitle("DAO CRUD Test (Updated)");
	            fetched.setGenre("Test-Updated");
	            boolean updated = dao.updateBook(fetched);
	            System.out.println("\nUpdated? " + updated);

	            Book afterUpdate = dao.getBookById(newId);
	            System.out.println("After update:");
	            System.out.println(afterUpdate.getId() + " | " + afterUpdate.getTitle() + " | " + afterUpdate.getGenre());

	            // SEARCH
	            System.out.println("\nSearch for 'updated':");
	            print(dao.searchBooks("updated"));

	            // DELETE
	            boolean deleted = dao.deleteBook(newId);
	            System.out.println("\nDeleted? " + deleted);

	            System.out.println("\n---- Final books ----");
	            print(dao.getAllBooks());

	            System.out.println("\n✅ DAO CRUD fully working.");

	        } catch (Exception e) {
	            System.out.println("❌ DAO test failed.");
	            e.printStackTrace();
	        }
	    }

	    private static void print(List<Book> books) {
	        for (Book b : books) {
	            System.out.println(b.getId() + " | " + b.getTitle() + " | " + b.getAuthor() + " | " + b.getYearPublished() + " | " + b.getGenre());
	        }
	    }
}