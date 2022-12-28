package com.example.services;

import com.example.entity.Book;
import com.example.entity.OwnedBook;
import com.example.entity.User;

public interface OwnedBookService extends CrudService<OwnedBook, Long> {
    OwnedBook findOwnedBookByBookAndUser(Book book, User user);
}
