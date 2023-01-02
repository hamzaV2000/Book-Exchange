package com.example.repositories;

import com.example.entity.BookExchange;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByBookExchange(BookExchange bookExchange);
}
