package com.example.library;

import java.util.Objects;

public class book {

    public int sNo;
    public String bookName;
    public String authorName;
    public int bookQty;
    public int bookQtyCopy;

    // Default constructor
    public book() {}

    // Constructor with parameters for testing
    public book(int sNo, String bookName, String authorName, int bookQty) {
        this.sNo = sNo;
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookQty = bookQty;
        this.bookQtyCopy = bookQty;
    }

    // Method to check out a book
    public boolean checkOutBook() {
        if (this.bookQtyCopy > 0) {
            this.bookQtyCopy--;  // Decrease the number of available copies
            return true;         // Return true indicating success
        }
        return false;            // Return false if no copies are available
    }

    // Method to check in a book
    public void checkInBook() {
        this.bookQtyCopy++;  // Increase the number of available copies when returned
    }

    // Setter for bookQty that also updates bookQtyCopy
    public void setBookQty(int bookQty) {
        this.bookQty = bookQty;
        this.bookQtyCopy = bookQty;  // Ensuring bookQtyCopy is updated to match the total quantity
    }

    // Override equals() method to compare content of books
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if both objects are the same instance
        if (obj == null || getClass() != obj.getClass()) return false; // Check for null and class mismatch
        book other = (book) obj;
        return sNo == other.sNo &&
               bookQty == other.bookQty &&
               bookQtyCopy == other.bookQtyCopy &&
               Objects.equals(bookName, other.bookName) &&
               Objects.equals(authorName, other.authorName); // Compare fields for equality
    }

    // Override hashCode() method to ensure consistency with equals()
    @Override
    public int hashCode() {
        return Objects.hash(sNo, bookName, authorName, bookQty, bookQtyCopy); // Generate hash code based on fields
    }

    @Override
    public String toString() {
        return "Book { " +
                "S.No=" + sNo +
                ", Name='" + bookName + '\'' +
                ", Author='" + authorName + '\'' +
                ", Quantity=" + bookQty +
                ", Available Copies=" + bookQtyCopy +
                " }";
    }
}
