package com.example.library;

public class books {
    book[] theBooks = new book[50]; // Initialize array with capacity of 50 books
    public static int count = 0;

    // Method to add a book
    public void addBook(book b) {
        if (b == null) {
            System.out.println("Cannot add a null book.");
            return;
        }

        for (int i = 0; i < count; i++) {
            if (theBooks[i] != null && b.sNo == theBooks[i].sNo) {
                System.out.println("Book of Serial No " + b.sNo + " is Already Added.");
                return;
            }
        }

        if (count < theBooks.length) {
            theBooks[count] = b;
            count++;
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Cannot add more books. The limit is reached.");
        }
    }

    // Method to search a book by serial number
    public book searchBySno(int sNo) {
        for (int i = 0; i < count; i++) {
            if (theBooks[i] != null && theBooks[i].sNo == sNo) {
                return theBooks[i];
            }
        }
        return null;
    }

    // Method to search books by author's name
    public book[] searchByAuthorName(String authorName) {
        book[] foundBooks = new book[50];
        int foundCount = 0;
        for (int i = 0; i < count; i++) {
            if (theBooks[i] != null && theBooks[i].authorName.equalsIgnoreCase(authorName)) {
                foundBooks[foundCount] = theBooks[i];
                foundCount++;
            }
        }
        book[] result = new book[foundCount];
        System.arraycopy(foundBooks, 0, result, 0, foundCount);
        return result;
    }

    // Method to upgrade the quantity of books
    public void upgradeBookQty(int sNo, int qty) {
        book b = searchBySno(sNo);
        if (b != null) {
            if ((long) b.bookQty + qty > Integer.MAX_VALUE) {
                b.bookQty = Integer.MAX_VALUE;
                b.bookQtyCopy = Integer.MAX_VALUE;
            } else if ((long) b.bookQty + qty < Integer.MIN_VALUE) {
                b.bookQty = Integer.MIN_VALUE;
                b.bookQtyCopy = Integer.MIN_VALUE;
            } else {
                b.bookQty += qty;
                b.bookQtyCopy += qty;
            }
        }
    }

    public boolean decreaseBookQty(int sNo, int qty) {
        book b = searchBySno(sNo);
        if (b != null) {
            if (qty <= 0) {
                return false;  // Reject zero or negative quantities
            }
            if (b.bookQtyCopy - qty < 0) {
                System.out.println("Cannot decrease quantity below zero");
                return false;  // Prevent decreasing below zero
            }
            b.bookQtyCopy -= qty;  // Decrease the quantity
            b.bookQty -= qty;      // Decrease the total quantity as well
            return true;
        }
        return false;  // Book not found
    }

    // Method to display all books
    public void showAllBooks() {
        System.out.println("S.No\t\t\tBook Name\t\t\tAuthor Name\t\t\tAvailable Qty\t\t\tTotal Qty");
        for (int i = 0; i < count; i++) {
            if (theBooks[i] != null) {
                System.out.println(theBooks[i].sNo + "\t\t\t" + theBooks[i].bookName + "\t\t\t" + 
                                 theBooks[i].authorName + "\t\t\t" + theBooks[i].bookQtyCopy + 
                                 "\t\t\t" + theBooks[i].bookQty);
            }
        }
    }

    // Method to check out a book
    public book checkOutBook(int sNo) {
        book b = searchBySno(sNo);
        if (b != null && b.bookQtyCopy > 0) {
            b.bookQtyCopy--;
            return b;
        }
        return null;
    }

    // Method to check in a book
    public void checkInBook(book b) {
        if (b == null) {
            System.out.println("Cannot check in a null book.");
            return;
        }

        // Check if the book exists in the library
        for (int i = 0; i < count; i++) {
            if (theBooks[i] != null && theBooks[i].sNo == b.sNo) {
                // Verify that the book was checked out before allowing check-in
                if (theBooks[i].bookQtyCopy < theBooks[i].bookQty) {
                    theBooks[i].bookQtyCopy++; // Increment available copies
                    System.out.println("Book of Serial No " + b.sNo + " has been successfully checked in.");
                } else {
                    System.out.println("Book of Serial No " + b.sNo + " was not checked out.");
                }
                return;
            }
        }

        // If the book is not found in the library
        System.out.println("Book of Serial No " + b.sNo + " does not exist in the library.");
    }
}