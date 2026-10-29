package com.jj.bookservice.service;

import com.jj.bookservice.model.Book;
import com.jj.bookservice.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        logger.info("We are in getAllBooks method {} {}","Jeevan Baba","Jay Ho");
       return this.bookRepository.findAll();
    }

    @Cacheable(value = "bookcache" , key = "#bid")
    public Book getSingleBook(int bid) {
        return this.bookRepository.findById(bid).get();
    }

    @Transactional
    public Book addBook(Book book) {
        if(checkbookname(book.getTitle())) {
            return this.bookRepository.save(book);
        }

        throw new RuntimeException("Product title is empty");
    }

    private boolean checkbookname(String title) {
        return !title.isEmpty();
    }

    @CacheEvict(value = "bookcache", key = "#bid")
    public void deleteBook(int bid) {
        this.bookRepository.deleteById(bid);
    }

    @CachePut(value = "bookcache", key ="#book.bid" )
    public Book updateBook(Book book) {
        return this.bookRepository.save(book);
    }
}
