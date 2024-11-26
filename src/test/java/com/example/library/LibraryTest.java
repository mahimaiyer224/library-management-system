package com.example.library;

import org.junit.jupiter.api.BeforeEach;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
public class LibraryTest {
    private books bookCollection;
    private students studentCollection;
    private book testBook;
    private student testStudent;

    @BeforeEach
    void setUp() {
        bookCollection = new books();
        studentCollection = new students();
        // Reset the static count variables before each test
        books.count = 0;
        students.count = 0;
        // Create a test book and student that can be used across multiple tests
        testBook = new book(1, "Test Book", "Test Author", 5);
        testStudent = new student("John Doe", "12345");
    }
    



    @Test
    void testDisplayBookDetails() {
        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the method
        Library.displayBookDetails(testBook);

        // Assert the captured output
        String expectedOutput = "S.No: 1\n" +
                "Name: Test Book\n" +
                "Author: Test Author\n" +
                "Available Qty: 5\n" +
                "Total Qty: 5\n";

        String actualOutput = outContent.toString().trim().replace("\r\n", "\n");
        assertEquals(expectedOutput.trim(), actualOutput);


        // Reset System.out
        System.setOut(System.out);
    }
    @Test
    void testCreateBook() {
        book newBook = Library.createBook(1, "Test Book", "Test Author", 5);
        
        assertNotNull(newBook);
        assertEquals(1, newBook.sNo);
        assertEquals("Test Book", newBook.bookName);
        assertEquals("Test Author", newBook.authorName);
        assertEquals(5, newBook.bookQty);
        assertEquals(5, newBook.bookQtyCopy);
    }

    @Test
    void testUpgradeBookQty() {
        // First add the book to the collection
        bookCollection.addBook(testBook);
        int initialQty = testBook.bookQty;
        
        // Now upgrade the quantity
        Library.upgradeBookQty(bookCollection, 1, 3);
        
        // Search for the book again
        book foundBook = bookCollection.searchBySno(1);
        assertNotNull(foundBook, "Book should be found after upgrading quantity");
        assertEquals(initialQty + 3, foundBook.bookQty);
        assertEquals(initialQty + 3, foundBook.bookQtyCopy);
    }

    @Test
    void testCreateStudent() {
        student newStudent = Library.createStudent("John Doe", "12345");
        
        assertNotNull(newStudent);
        assertEquals("John Doe", newStudent.studentName);
        assertEquals("12345", newStudent.regNum);
    }

    @Test
    void testHandleSearchBySno() {
        // First add the book to the collection
        bookCollection.addBook(testBook);
        
        // Now search for it
        book foundBook = bookCollection.searchBySno(1);
        assertNotNull(foundBook, "Book should be found after adding to collection");
        assertEquals("Test Book", foundBook.bookName);
        assertEquals("Test Author", foundBook.authorName);
    }

    @Test
    void testHandleSearchBySnoNotFound() {
        book foundBook = bookCollection.searchBySno(999);
        assertNull(foundBook);
    }

    @Test
    void testHandleSearchByAuthorName() {
        // Add multiple books by the same author
        book testBook1 = new book(1, "Test Book 1", "Test Author", 5);
        book testBook2 = new book(2, "Test Book 2", "Test Author", 3);
        bookCollection.addBook(testBook1);
        bookCollection.addBook(testBook2);
        
        // Search for books
        book[] foundBooks = bookCollection.searchByAuthorName("Test Author");
        
        // Verify results
        assertNotNull(foundBooks, "Found books array should not be null");
        assertTrue(foundBooks.length > 0, "Should find at least one book");
        assertEquals("Test Author", foundBooks[0].authorName);
        assertEquals(2, foundBooks.length, "Should find exactly two books");
    }

    @Test
    void testHandleSearchByAuthorNameNotFound() {
        book[] foundBooks = bookCollection.searchByAuthorName("Nonexistent Author");
        
        assertNotNull(foundBooks, "Found books array should not be null even when empty");
        assertEquals(0, foundBooks.length, "Should return empty array for non-existent author");
    }
    @Test
    void testHandleSearchBySnoNotFoundMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Library.handleSearchBySno(bookCollection, 999);

        assertEquals("No Book for Serial No 999 Found.".trim(), outContent.toString().trim());
        System.setOut(System.out);
    }

    @Test
    void testHandleSearchByAuthorNameNotFoundMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Library.handleSearchByAuthorName(bookCollection, "Unknown Author");

        assertEquals("No Books of Unknown Author Found.".trim(), outContent.toString().trim());
        System.setOut(System.out);
    }

}
