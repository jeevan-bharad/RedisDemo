package com.jj.bookservice.controller;

import com.jj.bookservice.model.Book;
import com.jj.bookservice.service.BookService;
import com.jj.bookservice.service.CacheInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    
    @Autowired
    CacheInspectionService cacheInspectionService;

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks()
    {
        return  ResponseEntity.ok(this.bookService.getAllBooks());

    }

    @GetMapping("/{bid}")
    public ResponseEntity<Book> getSingleBooks(@PathVariable("bid") int bid)
    {
        return ResponseEntity.ok(this.bookService.getSingleBook(bid));

    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book)
    {
       return ResponseEntity.ok(this.bookService.addBook(book));

    }

    @DeleteMapping("/{bid}")
    public ResponseEntity<?> deleteBook(@PathVariable("bid") int bid){
        this.bookService.deleteBook(bid);
      return  ResponseEntity.ok().build();

    }

    @PutMapping()
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
       return ResponseEntity.ok(this.bookService.updateBook(book));

    }

    @GetMapping("/cacheData")
    public void getCacheDate() {
        cacheInspectionService.printCacheContents("bookcache");
    }

}
