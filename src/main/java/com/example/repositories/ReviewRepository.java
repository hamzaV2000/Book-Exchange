package com.example.repositories;

import com.example.entity.Book;
import com.example.entity.Review;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUser(User user);
    Review findReviewByBookAndUser(Book book, User user);
}
