package com.example.library;

import java.util.ArrayList;
import java.util.List;

public class students {

    student[] theStudents = new student[50];
    private List<student> studentList = new ArrayList<>();

    public static int count = 0;

    public boolean addStudent(student newStudent) {
        // Check for duplicates
        if (isStudent(newStudent.getRegNum()) != -1) {
            return false; // Already exists
        }

        for (int i = 0; i < theStudents.length; i++) {
            if (theStudents[i] == null) {
                theStudents[i] = newStudent;
                count++; // Increment the count
                return true;
            }
        }
        return false; // No space to add a new student
    }

    public int isStudent(String regNum) {
        for (int i = 0; i < theStudents.length; i++) {
            if (theStudents[i] != null && theStudents[i].getRegNum().equalsIgnoreCase(regNum)) {
                return i;
            }
        }
        return -1; // Student not found
    }

    public void showAllStudents() {
        System.out.println("Student Name\t\tReg Number");
        for (int i = 0; i < count; i++) {
            System.out.println(theStudents[i].studentName + "\t\t" + theStudents[i].regNum);
        }
    }

    public void checkOutBook(books book, String regNum, int sNo) {
        int studentIndex = this.isStudent(regNum);
        if (studentIndex != -1) {
            book.showAllBooks();

            book b = book.checkOutBook(sNo);
            if (b != null) {
                for (int i = 0; i < theStudents[studentIndex].booksCount; i++) {
                    if (theStudents[studentIndex].borrowedBooks[i].sNo == sNo) {
                        System.out.println("This book has already been checked out by the student.");
                        return;
                    }
                }

                if (b.bookQtyCopy > 0) {
                    if (theStudents[studentIndex].booksCount < 3) {
                        theStudents[studentIndex].borrowedBooks[theStudents[studentIndex].booksCount] = b;
                        theStudents[studentIndex].booksCount++;
                        b.bookQtyCopy--;
                    } else {
                        System.out.println("Student cannot borrow more than 3 books.");
                    }
                } else {
                    System.out.println("Book is not available.");
                }
            } else {
                System.out.println("Book is not available.");
            }
        }
    }

    public void checkInBook(book b) {
        b.bookQtyCopy++;
    }

    public void checkInBook(books book, String regNum, int sNo) {
        int studentIndex = this.isStudent(regNum);
        if (studentIndex != -1) {
            student s = theStudents[studentIndex];
            for (int i = 0; i < s.booksCount; i++) {
                if (sNo == s.borrowedBooks[i].sNo) {
                    book.checkInBook(s.borrowedBooks[i]);
                    s.borrowedBooks[i] = null;

                    for (int j = i; j < s.booksCount - 1; j++) {
                        s.borrowedBooks[j] = s.borrowedBooks[j + 1];
                    }
                    s.booksCount--;
                    return;
                }
            }
            System.out.println("Book of Serial No " + sNo + " not found.");
        }
    }

    public student searchByRegNum(String regNum) {
        for (student s : studentList) {
            if (s.getRegNum().equals(regNum)) {
                return s;
            }
        }
        return null;
    }
}
