package com.example.services.impl;

import com.example.entity.Book;
import com.example.entity.Rating;
import com.example.repositories.RatingRepository;
import com.example.services.RatingService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Set<Rating> findAll() {
        return null;
    }

    @Override
    public Rating findById(Long aLong) {
        return ratingRepository.findById(aLong).orElse(null);
    }

    @Override
    public Rating save(Rating object) {
        return ratingRepository.save(object);
    }

    @Override
    public void delete(Rating object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Rating findRatingByBook(Book book) {
        return ratingRepository.findRatingByBook(book);
    }
}
