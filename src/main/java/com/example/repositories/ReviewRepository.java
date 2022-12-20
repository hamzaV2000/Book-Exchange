package com.example.repositories;

import com.example.entity.Review;
import org.springframework.data.repository.CrudRepository;


public interface ReviewRepository extends CrudRepository<Review, Long> {
}
