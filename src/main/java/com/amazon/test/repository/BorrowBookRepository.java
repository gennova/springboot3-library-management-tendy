package com.amazon.test.repository;

import com.amazon.test.model.BorrowBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowBookRepository extends JpaRepository<BorrowBook, Long>{

}
