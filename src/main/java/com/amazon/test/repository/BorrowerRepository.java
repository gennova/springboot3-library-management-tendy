package com.amazon.test.repository;

import com.amazon.test.model.Book;
import com.amazon.test.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long>{
    Borrower findFirstById(Long id);
}
