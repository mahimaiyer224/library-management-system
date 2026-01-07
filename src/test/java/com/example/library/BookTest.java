package com.example.library;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookTest {

    private book testBook;

    @BeforeEach
    public void setUp() {
        testBook = new book();
        testBook.sNo = 1;
        testBook.bookName = "Test Book";
        testBook.authorName = "Test Author";
        testBook.bookQty = 5;
        testBook.bookQtyCopy = 5;
    }

    @Test
    public void testBookDetails() {
        assertEquals(1, testBook.sNo);
        assertEquals("Test Book", testBook.bookName);
        assertEquals("Test Author", testBook.authorName);
        assertEquals(5, testBook.bookQty);
        assertEquals(5, testBook.bookQtyCopy);
    }

    @Test
    public void testParameterizedConstructor() {
        book paramBook = new book(2, "Test Book 2", "Test Author 2", 10);
        assertEquals(2, paramBook.sNo);
        assertEquals("Test Book 2", paramBook.bookName);
        assertEquals("Test Author 2", paramBook.authorName);
        assertEquals(10, paramBook.bookQty);
        assertEquals(10, paramBook.bookQtyCopy);
    }

    @Test
    public void testCheckOutBook() {
        // Check out a book
        boolean result = testBook.checkOutBook();
        assertTrue(result);
        assertEquals(4, testBook.bookQtyCopy);

        // Check out again until no copies are left
        for (int i = 0; i < 4; i++) {
            testBook.checkOutBook();
        }
        assertFalse(testBook.checkOutBook()); // No copies left, should return false
    }

    @Test
    public void testCheckInBook() {
        testBook.checkOutBook(); // Check out 1 book
        assertEquals(4, testBook.bookQtyCopy);
        testBook.checkInBook(); // Check in the book
        assertEquals(5, testBook.bookQtyCopy); // Book quantity should return to original
    }

    @Test
    public void testSetBookQty() {
        testBook.setBookQty(10);
        assertEquals(10, testBook.bookQty);
        assertEquals(10, testBook.bookQtyCopy);
    }

    @Test
    public void testEqualsMethod() {
        book otherBook = new book(1, "Test Book", "Test Author", 5);
        assertTrue(testBook.equals(otherBook));

        // Mutate the book details and test again
        otherBook.bookQty = 10;
        assertFalse(testBook.equals(otherBook));
    }

    @Test
    public void testHashCode() {
        book otherBook = new book(1, "Test Book", "Test Author", 5);
        assertEquals(testBook.hashCode(), otherBook.hashCode());
    }

    @Test
    public void testInvalidBookQty() {
        book negativeQtyBook = new book(4, "Negative Qty", "Author", -5);
        assertEquals(-5, negativeQtyBook.bookQty);  // Testing if negative value is accepted
        assertEquals(-5, negativeQtyBook.bookQtyCopy); // Book quantity copy should also match
    }

    @Test
    public void testZeroBookQty() {
        book zeroQtyBook = new book(5, "Zero Qty", "Author", 0);
        assertEquals(0, zeroQtyBook.bookQty);
        assertEquals(0, zeroQtyBook.bookQtyCopy); // No copies available
    }

    // Test for object equality
    @Test
    public void testBookEquality() {
        book book1 = new book(1, "Test Book", "Test Author", 5);
        book book2 = new book(1, "Test Book", "Test Author", 5);
        assertTrue(book1.equals(book2), "Books should be equal");
        assertTrue(book2.equals(book1), "Equality is symmetric");
        assertEquals(book1.hashCode(), book2.hashCode(), "Hash codes should be equal");
    }

    @Test
    public void testBookHashCode() {
        book book1 = new book(1, "Test Book", "Test Author", 5);
        book book2 = new book(1, "Test Book", "Test Author", 5);
        assertEquals(book1.hashCode(), book2.hashCode(), "Hash codes should be the same for equal objects");
    }
}
