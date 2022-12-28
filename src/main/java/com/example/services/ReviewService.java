package com.example.services;

import com.example.entity.Book;
import com.example.entity.Review;
import com.example.entity.User;

import java.util.List;

public interface ReviewService extends CrudService<Review, Long>{
    List<Review> findAllByUser(User user);
    Review findReviewByBookAndUser(Book book, User user);
}
