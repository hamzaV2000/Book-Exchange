package com.example.repositories;

import com.example.entity.Book;
import com.example.entity.OwnedBook;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OwnedBookRepository extends JpaRepository<OwnedBook, Long> {
    OwnedBook findOwnedBookByBookAndUser(Book book, User user);
}
