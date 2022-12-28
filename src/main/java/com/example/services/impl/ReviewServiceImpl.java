package com.example.services.impl;

import com.example.entity.Book;
import com.example.entity.Rating;
import com.example.entity.Review;
import com.example.entity.User;
import com.example.repositories.ReviewRepository;
import com.example.services.RatingService;
import com.example.services.ReviewService;
import com.example.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final UserService userService;
    private final ReviewRepository reviewRepository;

    private final RatingService ratingService;

    public ReviewServiceImpl(UserService userService, ReviewRepository reviewRepository, RatingService ratingService) {
        this.userService = userService;
        this.reviewRepository = reviewRepository;
        this.ratingService = ratingService;
    }

    @Override
    public Set<Review> findAll() {
        return null;
    }

    @Override
    public Review findById(Long aLong) {
        return reviewRepository.findById(aLong).orElse(null);
    }

    @Override
    public Review save(Review object) {
        Rating rating_entity = ratingService.findRatingByBook(object.getBook());
        Review review = reviewRepository.findReviewByBookAndUser(object.getBook(), object.getUser());
        if(review == null){
            reviewRepository.save(object);
            rating_entity.addStar(object.getUserRating());
        }

        else{
            rating_entity.removeStar(review.getUserRating()); //remove the old one
            rating_entity.addStar(object.getUserRating()); // add the new one

            review.setUserRating(object.getUserRating());
            reviewRepository.save(review);


        }
        ratingService.save(rating_entity);
        return review;
    }

    @Override
    public void delete(Review object) {
        reviewRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        reviewRepository.deleteById(aLong);
    }

    @Override
    public List<Review> findAllByUser(User user) {
        return reviewRepository.findAllByUser(user);
    }

    @Override
    public Review findReviewByBookAndUser(Book book, User user) {
        return reviewRepository.findReviewByBookAndUser(book, user);
    }

}
