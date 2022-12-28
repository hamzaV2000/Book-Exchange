package com.example.services;

import com.example.entity.Book;
import com.example.entity.Rating;

public interface RatingService extends CrudService<Rating, Long> {
    Rating findRatingByBook(Book book);
}
