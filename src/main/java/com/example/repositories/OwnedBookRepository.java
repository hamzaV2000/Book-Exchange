package com.example.repositories;

import com.example.entity.OwnedBook;
import org.springframework.data.repository.CrudRepository;


public interface OwnedBookRepository extends CrudRepository<OwnedBook, Long> {
}
