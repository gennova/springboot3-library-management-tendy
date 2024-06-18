package com.amazon.test.controller;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazon.test.model.BookStock;
import com.amazon.test.model.BorrowBook;
import com.amazon.test.model.Borrower;
import com.amazon.test.repository.BookRepository;
import com.amazon.test.repository.BorrowBookRepository;
import com.amazon.test.repository.BorrowerRepository;
import com.amazon.test.services.BookService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.v3.core.util.Json;
import jakarta.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.test.exception.ResourceNotFoundException;
import com.amazon.test.model.Book;

@RestController
@RequestMapping("/api/v1")
public class BookStoreController {
    Logger logger = LoggerFactory.getLogger(BookStoreController.class);

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowerRepository borrowerRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowBookRepository borrowBookRepository;


    @GetMapping("/books")
    public List<Book> getAllBooks() {
        logger.info("Started get all book");
        return bookRepository.findAll();
    }

    @GetMapping("/books/count")
    public Long Count() {
        logger.info("Started get count  book");
        return bookRepository.count();
    }

    @GetMapping("/books/stocksAll")
    public List<BookStock> getAllBookStocks() throws SQLException {
        logger.info("Started get stocks  book");
        return bookService.getStock();
    }

    @GetMapping("/books/getBookByISBN/{isbn}")
    public Book getBookByISBN(
            @PathVariable(value = "isbn") String isbn) {
        logger.info("Started get book by isbn"+isbn);
        return bookRepository.findFirstByIsbn(isbn);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(
            @PathVariable(value = "id") Long bookId) throws ResourceNotFoundException {
        logger.info("Started get book by id");
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found :: " + bookId));
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/books/save")
    public String createBook(@Valid @RequestBody Book book) {
        Map hash = new HashMap<String,String>();
        logger.info("Started save book");
        Book book_exist = bookRepository.findFirstByIsbn(book.getIsbn());
        if(book_exist!=null){
            System.out.println(book_exist.getTitle());
            System.out.println(book.getTitle());
            if (!book_exist.getTitle().equals(book.getTitle()) || !book_exist.getAuthor().equals(book.getAuthor())){
                hash.put("status","200");
                hash.put("message","Book with ISBN exist, title and author must be same");
                logger.info("Book with ISBN exist, title and author must be same ");
                new ResourceNotFoundException("Book with ISBN exist, title and author must be same ");
            }else{
                bookRepository.save(book);
                hash.put("status","200");
                hash.put("message","Book saved successfully");
            }
        }else{
            bookRepository.save(book);
            hash.put("status","200");
            hash.put("message","Book saved successfully");
        }
        return new JSONObject(hash).toString();
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable(value = "id") Long bookId,
            @Valid @RequestBody Book bookDetails) throws ResourceNotFoundException {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found :: " + bookId));

        book.setIsbn(bookDetails.getIsbn());
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        final Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/books/{id}")
    public Map<String, Boolean> deleteBook(
            @PathVariable(value = "id") Long bookId) throws ResourceNotFoundException {
        logger.info("Started delete book book");
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found :: " + bookId));

        bookRepository.delete(book);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    //borrower
    @GetMapping("/borrowers")
    public List<Borrower> getAllBorrowers() {
        logger.info("Started get all borrower");
        return borrowerRepository.findAll();
    }

    @GetMapping("/borrowers/{id}")
    public ResponseEntity<Borrower> getBorrowerById(
            @PathVariable(value = "id") Long borrowerId) throws ResourceNotFoundException {
        logger.info("Started get borrower by id");
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found :: " + borrowerId));
        return ResponseEntity.ok().body(borrower);
    }

    @PostMapping("/borrowers")
    public Borrower createBorrower(@Valid @RequestBody Borrower borrower) {
        logger.info("Started save borrower");
        return borrowerRepository.save(borrower);
    }

    @PutMapping("/borrowers/{id}")
    public ResponseEntity<Borrower> updateBorrower(
            @PathVariable(value = "id") Long borrowerId,
            @Valid @RequestBody Borrower borrowerDetails) throws ResourceNotFoundException {
        logger.info("Started update borrower");
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found :: " + borrowerId));

        borrower.setName(borrowerDetails.getName());
        final Borrower updatedBorrower = borrowerRepository.save(borrower);
        return ResponseEntity.ok(updatedBorrower);
    }

    @DeleteMapping("/borrowers/{id}")
    public Map<String, Boolean> deleteBorrower(
            @PathVariable(value = "id") Long borrowerId) throws ResourceNotFoundException {
        logger.info("Started delete borrower");
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found :: " + borrowerId));

        borrowerRepository.delete(borrower);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    //library activity borrow a book
    @PostMapping("/borrowbook/{isbn}/{idborrower}")
    public BorrowBook createBorrowBook(String isbn,Long borrowerId) throws SQLException {
        BorrowBook borrowBook = new BorrowBook();
        Book book = bookRepository.findFirstByIsbn(isbn);
        Borrower borrower = borrowerRepository.findFirstById(borrowerId);
        List<BookStock> checkstok = bookService.getStock();
        for (BookStock bookStock:checkstok){
            if (book.getIsbn().equals(bookStock.getIsbn())){
                if (bookStock.getAvailable()>0){
                    logger.info("Started save borrow book"+" "+isbn+" "+borrowerId);
                    System.out.println(book.toString());
                    System.out.println(borrower.toString());
                    borrowBook = new BorrowBook();
                    borrowBook.setIsbn(book.getIsbn());
                    borrowBook.setTitle(book.getTitle());
                    borrowBook.setAuthor(book.getAuthor());
                    borrowBook.setName(borrower.getName());
                    borrowBook.setDateborrow(new GregorianCalendar().getTime().toString());
                    borrowBookRepository.save(borrowBook);
                }else{
                    throw new SQLException("Book out of stock");
                }
            }
        }
        logger.info("Started save borrow book"+" "+isbn+" "+borrowerId);
        System.out.println(book.toString());
        System.out.println(borrower.toString());
        return borrowBookRepository.save(borrowBook);
    }

    @DeleteMapping("/borrowbook/returningbook/{id}")
    public Map<String, Boolean> deleteBorrowBook(
            @PathVariable(value = "id") Long borrowerId) throws ResourceNotFoundException {
        logger.info("Started delete borrow book");
        BorrowBook borrowBook = borrowBookRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowbook not found :: " + borrowerId));

        borrowBookRepository.delete(borrowBook);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted aka returned", Boolean.TRUE);
        return response;
    }
}
