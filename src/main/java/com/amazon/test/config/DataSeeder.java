package com.amazon.test.config;

import com.amazon.test.model.Book;
import com.amazon.test.model.Borrower;
import com.amazon.test.repository.BookRepository;
import com.amazon.test.repository.BorrowerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowerRepository borrowerRepository;

    @PostConstruct
    public void initData() {
        Book book1 = new Book("1000", "Game of throne V1","George R. R. Martin"); //publish 2000
        Book book2 = new Book("1000", "Game of throne V1","George R. R. Martin"); //publish 2000
        Book book3 = new Book("1000", "Game of throne V1","George R. R. Martin"); //publish 2000
        Book book4 = new Book("1001", "Game of throne V1","George R. R. Martin"); //publish 2001
        Book book5 = new Book("1001", "Game of throne V1","George R. R. Martin"); //publish 2001
        Book book6 = new Book("1002", "Game of throne V2","George R. R. Martin");
        Book book7 = new Book("1003", "Game of throne V3","George R. R. Martin");
        Book book8 = new Book("1004", "The Hunger Games","Suzanne Collins");

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        bookRepository.save(book5);
        bookRepository.save(book6);
        bookRepository.save(book7);
        bookRepository.save(book8);

        Borrower borrower1 = new Borrower(1L,"Tendy");
        Borrower borrower2 = new Borrower(2L,"Joy");
        Borrower borrower3 = new Borrower(3L,"John");
        Borrower borrower4= new Borrower(4L,"Susan");
        Borrower borrower5 = new Borrower(5L,"Helen");
        borrowerRepository.save(borrower1);
        borrowerRepository.save(borrower2);
        borrowerRepository.save(borrower3);
        borrowerRepository.save(borrower4);
        borrowerRepository.save(borrower5);
    }
}
