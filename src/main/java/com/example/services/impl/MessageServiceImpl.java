package com.example.services.impl;

import com.example.entity.BookExchange;
import com.example.entity.Message;
import com.example.repositories.MessageRepository;
import com.example.services.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Set<Message> findAll() {
        return null;
    }

    @Override
    public Message findById(Long aLong) {
        return null;
    }

    @Override
    public Message save(Message object) {
        return messageRepository.save(object);
    }

    @Override
    public void delete(Message object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<Message> findAllByBookExchange(BookExchange bookExchange) {
        return messageRepository.findAllByBookExchange(bookExchange);
    }
}
