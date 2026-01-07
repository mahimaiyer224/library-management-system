package com.example.library;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BooksTest {
    private books testBooks;
    private book testBook;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        testBooks = new books();
        books.count = 0; // Reset static count before each test
        testBook = new book(1, "Test Book", "Test Author", 5);
        testBooks.addBook(testBook);
    }

    @Test
    public void testAddBook() {
        book newBook = new book(1, "Test Book", "Test Author", 5);
        testBooks.addBook(newBook); // Add the book
        assertTrue(testBooks.theBooks[0] != null); // Verify the book was added
    }

    @Test
    public void testAddBookWhenArrayIsFull() {
        // Fill the array to capacity
        for (int i = 2; i <= 50; i++) {
            testBooks.addBook(new book(i, "Book " + i, "Author " + i, 1));
        }
        assertEquals(50, books.count);
        
        // Try adding one more book
        book extraBook = new book(51, "Extra Book", "Extra Author", 1);
        testBooks.addBook(extraBook);
        assertEquals(50, books.count); // Count should not increase
        assertTrue(outContent.toString().contains("Cannot add more books."));
    }

    @Test
    public void testAddBookAtArrayBoundary() {
        testBooks = new books();
        books.count = 49; // One less than capacity
        book newBook = new book(1, "Last Book", "Last Author", 1);
        testBooks.addBook(newBook);
        assertEquals(50, books.count);
    }

    @Test
    public void testSearchBySno() {
        book result = testBooks.searchBySno(1);
        assertNotNull(result);
        assertEquals(testBook.bookName, result.bookName);
        
        // Test search in empty slot
        result = testBooks.searchBySno(999);
        assertNull(result);
    }

    @Test
    public void testSearchBySnoWithNull() {
        testBooks = new books(); // Create fresh instance
        books.count = 0; // Reset count
        assertNull(testBooks.searchBySno(1)); // Should return null when no books exist
    }

    @Test
    public void testSearchByAuthorName() {
        // Add another book by same author
        testBooks.addBook(new book(2, "Second Book", "Test Author", 3));
        
        book[] foundBooks = testBooks.searchByAuthorName("Test Author");
        assertEquals(2, foundBooks.length);
        
        // Test case insensitive search
        book[] foundBooksLower = testBooks.searchByAuthorName("test author");
        assertEquals(2, foundBooksLower.length);
    }

    @Test
    public void testSearchByAuthorNameWithEmptyLibrary() {
        books emptyBooks = new books();
        books.count = 0;
        book[] result = emptyBooks.searchByAuthorName("Any Author");
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchByNonExistentAuthor() {
        book[] noBooks = testBooks.searchByAuthorName("Non Existent");
        assertEquals(0, noBooks.length);
    }

    @Test
    public void testUpgradeBookQty() {
        testBooks.upgradeBookQty(1, 5);
        assertEquals(10, testBooks.theBooks[0].bookQty);
        assertEquals(10, testBooks.theBooks[0].bookQtyCopy);
        
        // Test upgrade with negative quantity
        testBooks.upgradeBookQty(1, -2);
        assertEquals(8, testBooks.theBooks[0].bookQty);
        assertEquals(8, testBooks.theBooks[0].bookQtyCopy);
        
        // Test upgrade for non-existent book
        testBooks.upgradeBookQty(999, 5);
        assertEquals(8, testBooks.theBooks[0].bookQty); // Should remain unchanged
    }

    @Test
    public void testUpgradeBookQtyWithZero() {
        int initialQty = testBooks.theBooks[0].bookQty;
        testBooks.upgradeBookQty(1, 0);
        assertEquals(initialQty, testBooks.theBooks[0].bookQty);
    }

    @Test
    public void testCheckOutBook() {
        // Pre-condition check
        assertNotNull(testBooks.theBooks[0]);
        assertTrue(testBooks.theBooks[0].bookQtyCopy > 0);
        
        book checkedOut = testBooks.checkOutBook(1);
        assertNotNull(checkedOut);
        assertEquals(4, testBooks.theBooks[0].bookQtyCopy);
        assertEquals(5, testBooks.theBooks[0].bookQty);
    }

    @Test
    public void testCheckOutBookWhenNoneAvailable() {
        // Checkout all copies
        while(testBooks.theBooks[0].bookQtyCopy > 0) {
            testBooks.checkOutBook(1);
        }
        // Try to checkout one more
        book result = testBooks.checkOutBook(1);
        assertNull(result);
    }

    @Test
    public void testCheckOutNonExistentBook() {
        book nonExistent = testBooks.checkOutBook(999);
        assertNull(nonExistent);
    }

    @Test
    public void testCheckInBook() {
        // First checkout a book
        book checkedOut = testBooks.checkOutBook(1);
        assertNotNull(checkedOut);
        assertEquals(4, testBooks.theBooks[0].bookQtyCopy);
        
        // Then check it back in
        testBooks.checkInBook(checkedOut);
        assertEquals(5, testBooks.theBooks[0].bookQtyCopy);
    }

    @Test
    public void testCheckInNullBook() {
        testBooks.checkInBook(null);
        assertEquals(5, testBooks.theBooks[0].bookQtyCopy); // Should remain unchanged
    }

    @Test
    public void testShowAllBooks() {
        testBooks.showAllBooks();
        String output = outContent.toString();
        assertTrue(output.contains("Test Book"));
        assertTrue(output.contains("Test Author"));
        assertTrue(output.contains("5")); // Quantity
        
        // Test with empty books array
        testBooks = new books();
        books.count = 0;
        outContent.reset();
        testBooks.showAllBooks();
        assertTrue(outContent.toString().contains("S.No")); // Header should still be present
    }

    @Test
    public void testDecreaseBookQty() {
        // Pre-condition check: Ensure book exists and has sufficient quantity
        assertNotNull(testBooks.theBooks[0]);
        assertEquals(5, testBooks.theBooks[0].bookQty);
        assertEquals(5, testBooks.theBooks[0].bookQtyCopy);
        
        // Decrease quantity by 2
        boolean result = testBooks.decreaseBookQty(1, 2);
        assertTrue(result); // Method should succeed
        assertEquals(3, testBooks.theBooks[0].bookQty); // Total quantity should decrease
        assertEquals(3, testBooks.theBooks[0].bookQtyCopy); // Available quantity should decrease
    }

    @Test
    public void testDecreaseBookQtyInsufficientStock() {
        boolean result = testBooks.decreaseBookQty(1, 10);  // Try to decrease more than available
        assertFalse(result);  // Should fail because there aren't enough copies
        assertEquals(5, testBooks.theBooks[0].bookQty);  // Quantity should remain unchanged
        assertEquals(5, testBooks.theBooks[0].bookQtyCopy);  // Check both quantities
    }


    @Test
    public void testDecreaseBookQtyNonExistentBook() {
        boolean result = testBooks.decreaseBookQty(999, 1); // Decrease non-existent book's quantity
        assertFalse(result); // Method should fail, as the book doesn't exist
    }
    @Test
    public void testDecreaseBookQtyBelowZero() {
        // First decrease the quantity by a valid amount
        testBooks.decreaseBookQty(1, 2);
        assertEquals(3, testBooks.theBooks[0].bookQtyCopy);
        assertEquals(3, testBooks.theBooks[0].bookQty);
        
        // Now try to decrease by more than available
        boolean result = testBooks.decreaseBookQty(1, 4);
        assertFalse(result);  // Should fail because it would go below zero
        // Quantities should remain at 3
        assertEquals(3, testBooks.theBooks[0].bookQtyCopy);
        assertEquals(3, testBooks.theBooks[0].bookQty);
    }

    @Test
    public void testDecreaseBookQtyInvalidInput() {
        boolean result = testBooks.decreaseBookQty(1, 0);  // Invalid input: zero quantity
        assertFalse(result); // This should return false as zero quantity is invalid
        result = testBooks.decreaseBookQty(1, -5); // Invalid input: negative quantity
        assertFalse(result); // This should return false as negative quantity is invalid
    }

}
