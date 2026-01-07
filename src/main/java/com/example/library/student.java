package com.example.library;

import java.util.Scanner;

public class student {
    // Class member variables
    String studentName;
    String regNum;
    book borrowedBooks[] = new book[3];
    public int booksCount = 0;

    // Default constructor for interactive input (keep this for the main application)
    public student() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Student Name:");
        this.studentName = input.nextLine();
        System.out.println("Enter Registration Number:");
        this.regNum = input.nextLine();
    }

    // Constructor for testing
    public student(String studentName, String regNum) {
        this.studentName = studentName;
        this.regNum = regNum;
    }

    public void borrowBook(book newBook) {
        if (booksCount < 3) {
            borrowedBooks[booksCount] = newBook;
            booksCount++;
        } else {
            System.out.println("Cannot borrow more than 3 books.");
        }
    }

    public String getRegNum() {
        return regNum;
    }
}
