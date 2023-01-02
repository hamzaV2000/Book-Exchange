package com.example.services;

import com.example.entity.BookExchange;
import com.example.entity.Message;

import java.util.List;

public interface MessageService extends CrudService<Message, Long> {
    List<Message> findAllByBookExchange(BookExchange bookExchange);
}
