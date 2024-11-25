package com.example.library;

public class Library {

    public static void main(String[] args) {
        books ob = new books();
        students obStudent = new students();

        // Manually creating a book and student for testing purposes
        book testBook = new book(1, "Test Book", "Test Author", 5);
        ob.addBook(testBook);

        student testStudent = new student("John Doe", "12345");
        obStudent.addStudent(testStudent);

        // Checking out and checking in the book without user input
        obStudent.checkOutBook(ob, "12345", 1);
        obStudent.checkInBook(ob, "12345", 1);

        ob.showAllBooks();
        obStudent.showAllStudents();
    }

    // Modified from private to package-private (removed private keyword)
    static book createBook(int sNo, String bookName, String authorName, int bookQty) {
        return new book(sNo, bookName, authorName, bookQty);
    }

    // Modified from private to package-private
    static void upgradeBookQty(books ob, int sNo, int addingQty) {
        ob.upgradeBookQty(sNo, addingQty);
    }

    // Modified from private to package-private
    static student createStudent(String studentName, String regNum) {
        return new student(studentName, regNum);
    }

    // Modified from private to package-private
    static void handleSearchBySno(books ob, int sNo) {
        book foundBook = ob.searchBySno(sNo);
        if (foundBook != null) {
            displayBookDetails(foundBook);
        } else {
            System.out.println("No Book for Serial No " + sNo + " Found.");
        }
    }

    // Modified from private to package-private
    static void handleSearchByAuthorName(books ob, String authorName) {
        book[] foundBooks = ob.searchByAuthorName(authorName);
        boolean found = false;
        for (book b : foundBooks) {
            if (b != null) {
                displayBookDetails(b);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No Books of " + authorName + " Found.");
        }
    }

    // Modified from private to package-private
    static void displayBookDetails(book b) {
        System.out.println("S.No: " + b.sNo);
        System.out.println("Name: " + b.bookName);
        System.out.println("Author: " + b.authorName);
        System.out.println("Available Qty: " + b.bookQtyCopy);
        System.out.println("Total Qty: " + b.bookQty);
    }
}
