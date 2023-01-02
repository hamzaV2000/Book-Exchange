package com.example.services;

import com.example.entity.Book;
import com.example.entity.BookExchange;
import com.example.entity.User;
import com.example.repositories.BookExchangeRepository;

import java.util.List;

public interface BookExchangeService extends CrudService<BookExchange, Long> {
    List<BookExchange> findAllByHim(User user);
    List<BookExchange> findAllByMe(User user);


}
