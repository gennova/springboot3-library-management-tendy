package com.amazon.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazon.test.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
