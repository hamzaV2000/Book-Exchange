package com.example.services.impl;

import com.example.entity.ReviewChanges;
import com.example.repositories.ReviewChangesRepository;
import com.example.services.ReviewChangesService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReviewChangesServiceImpl implements ReviewChangesService {
    private final ReviewChangesRepository reviewChangesRepository;

    public ReviewChangesServiceImpl(ReviewChangesRepository reviewChangesRepository) {
        this.reviewChangesRepository = reviewChangesRepository;
    }

    @Override
    public Set<ReviewChanges> findAll() {
        return null;
    }

    @Override
    public ReviewChanges findById(Long aLong) {
        return null;
    }

    @Override
    public ReviewChanges save(ReviewChanges object) {
        return reviewChangesRepository.save(object);
    }

    @Override
    public void delete(ReviewChanges object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
