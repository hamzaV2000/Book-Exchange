package com.example.repositories;

import com.example.entity.Book;
import com.example.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findRatingByBook(Book book);
}
