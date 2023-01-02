package com.example.services.impl;

import com.example.entity.BookExchange;
import com.example.entity.User;
import com.example.repositories.BookExchangeRepository;
import com.example.services.BookExchangeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookExchangeServiceImpl implements BookExchangeService {
    private final BookExchangeRepository bookExchangeRepository;

    public BookExchangeServiceImpl(BookExchangeRepository bookExchangeRepository) {
        this.bookExchangeRepository = bookExchangeRepository;
    }

    @Override
    public Set<BookExchange> findAll() {
        return null;
    }

    @Override
    public BookExchange findById(Long aLong) {
        return bookExchangeRepository.findById(aLong).orElse(null);
    }

    @Override
    public BookExchange save(BookExchange object) {
        return bookExchangeRepository.save(object);
    }

    @Override
    public void delete(BookExchange object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<BookExchange> findAllByHim(User user) {
        return bookExchangeRepository.findAllByHim(user);
    }

    @Override
    public List<BookExchange> findAllByMe(User user) {
        return bookExchangeRepository.findAllByMe(user);

    }
}
