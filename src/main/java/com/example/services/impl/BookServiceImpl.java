package com.example.services.impl;

import com.example.entity.Book;
import com.example.repositories.BookRepository;
import com.example.services.BookService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Set<Book> findAll() {
        return null;
    }

    @Override
    public Book findById(Long aLong) {
        return bookRepository.findById(aLong).orElse(null);
    }

    @Override
    public Book save(Book object) {
        return null;
    }

    @Override
    public void delete(Book object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
