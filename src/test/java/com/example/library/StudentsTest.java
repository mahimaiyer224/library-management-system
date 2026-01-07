package com.example.library;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentsTest {

    private students testStudents;
    private student testStudent;
    private books testBooks;
    private book testBook;

    @BeforeEach
    public void setUp() {
        testStudents = new students();
        testStudent = new student("John Doe", "12345");
        testBooks = new books();
        testBooks.count = 0;
        testBook = new book(1, "Test Book", "Test Author", 5);
        testBooks.addBook(testBook);
        testStudents.count = 0;
        testStudents.addStudent(testStudent);
    }

    @Test
    public void testAddStudent() {
        assertEquals(1, testStudents.count);
        assertEquals("John Doe", testStudents.theStudents[0].studentName);
        assertEquals("12345", testStudents.theStudents[0].regNum);
    }

    @Test
    public void testPreventDuplicateStudent() {
        student duplicateStudent = new student("John Doe", "12345");
        testStudents.addStudent(duplicateStudent); // Try adding the same student again
        assertEquals(1, testStudents.count); // Count should not increase
    }

    @Test
    public void testIsStudent() {
        assertEquals(0, testStudents.isStudent("12345"));
    }

    @Test
    public void testCheckOutBook() {
        book testBook = new book(1, "Test Book", "Author", 5);

        boolean result = testBook.checkOutBook();
        assertTrue(result);
        assertEquals(4, testBook.bookQtyCopy);
    }

    @Test
    public void testCheckOutBookLimit() {
        testBooks.addBook(new book(2, "Test Book 2", "Test Author", 5));
        testBooks.addBook(new book(3, "Test Book 3", "Test Author", 5));
        testBooks.addBook(new book(4, "Test Book 4", "Test Author", 5));

        testStudents.checkOutBook(testBooks, "12345", 1);
        testStudents.checkOutBook(testBooks, "12345", 2);
        testStudents.checkOutBook(testBooks, "12345", 3);
        testStudents.checkOutBook(testBooks, "12345", 4);

        assertEquals(3, testStudents.theStudents[0].booksCount);
    }

    @Test
    public void testCheckInBook() {
        book testBook = new book(1, "Test Book", "Author", 5);
        testStudents.checkOutBook(testBooks, "12345", 1);
        assertEquals(1, testStudents.theStudents[0].booksCount);
        testStudents.checkInBook(testBooks, "12345", 1);
        assertEquals(0, testStudents.theStudents[0].booksCount);
    }

    @Test
    public void testAddStudentWhenArrayIsFull() {
        for (int i = 1; i <= 50; i++) {
            testStudents.addStudent(new student("Student " + i, "REG" + i));
        }
        student extraStudent = new student("Overflow", "REG51");
        testStudents.addStudent(extraStudent);
        assertEquals(50, testStudents.count);
    }
}
