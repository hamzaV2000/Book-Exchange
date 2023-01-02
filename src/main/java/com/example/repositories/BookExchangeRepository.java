package com.example.repositories;

import com.example.entity.BookExchange;
import com.example.entity.OwnedBook;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookExchangeRepository extends JpaRepository<BookExchange, Long> {
    List<BookExchange> findAllByHim(User user);
    List<BookExchange> findAllByMe(User user);
}
