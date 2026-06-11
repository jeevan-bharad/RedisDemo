package com.jj.bookservice.service;

import com.jj.bookservice.model.Book;
import com.jj.bookservice.repository.BookRepository;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private static final Logger log = LoggerFactory.getLogger(BookServiceTest.class);
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;


    @Test
    void getAllBooks() {
        // bookService.getAllBooks();
    }

    @Test
    void getSingleBook() {
    }

    @Test
    void addBook() {
        Book book = new Book(1, "title", "author", "genre");

        when(bookRepository.save(book)).thenReturn(book);
        Book book1 = bookService.addBook(book);
        assertEquals(book1.getBid(), book.getBid());
    }

    @Test
    void addBookThrowException() {
        Book book = new Book(1, "", "author", "genre");
        assertThrows(RuntimeException.class, () ->
                {
                    bookService.addBook(book);
                }
        );

        verify(bookRepository,times(0)).save(book);

    }

    @Test
    void deleteBook() {
        Book book = new Book(1, "title", "author", "genre");
        Mockito.doNothing().when(bookRepository).deleteById(1);
        bookService.deleteBook(1);
        Mockito.verify(bookRepository, times(1)).deleteById(1);

    }

    @Test
    void updateBook() {
    }

    @Test
    void checkbooknameIsValidTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Book book = new Book(1, "title", "author", "genre");
        Method checkbookname = BookService.class.getDeclaredMethod("checkbookname", String.class);
        checkbookname.setAccessible(true);
        boolean title = (Boolean) checkbookname.invoke(bookService, "title");
        assertTrue(title);
    }

    @Test
    void checkbooknameIsInvalidTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Book book = new Book(1, "", "author", "genre");
        Method checkbookname = BookService.class.getDeclaredMethod("checkbookname", String.class);
        checkbookname.setAccessible(true);
        boolean title = (Boolean) checkbookname.invoke(bookService, "");
        assertFalse(title);
    }


}