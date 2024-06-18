package com.amazon.test.repository;

import com.amazon.test.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface BookRepository extends JpaRepository<Book, Long>{
    Book findFirstByIsbn(String isbn);

}


