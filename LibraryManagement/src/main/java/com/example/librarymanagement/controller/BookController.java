package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Book;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@RestController
@RequestMapping("/books")
public class BookController {

    // Store books in ArrayList
    ArrayList<Book> books = new ArrayList<>();

    // a) Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        books.add(book);

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // b) Get all books
    @GetMapping
    public ArrayList<Book> getAllBooks() {

        return books;
    }

    // c) Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {

        for (Book b : books) {
            if (b.getId().equals(id)) {
                return ResponseEntity.ok(b);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // d) Update book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Integer id,
            @RequestBody Book updatedBook) {

        for (Book b : books) {

            if (b.getId().equals(id)) {

                b.setTitle(updatedBook.getTitle());
                b.setAuthor(updatedBook.getAuthor());
                b.setCategory(updatedBook.getCategory());
                b.setAvailableCopies(updatedBook.getAvailableCopies());

                return ResponseEntity.ok(b);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // e) Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {

        for (Book b : books) {

            if (b.getId().equals(id)) {

                books.remove(b);

                return ResponseEntity.ok("Book deleted successfully");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Book not found");
    }
}