package com.example.services.impl;

import com.example.entity.Book;
import com.example.entity.OwnedBook;
import com.example.entity.User;
import com.example.repositories.OwnedBookRepository;
import com.example.services.OwnedBookService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OwnedBookServiceImpl implements OwnedBookService {
    private final OwnedBookRepository ownedBookRepository;

    public OwnedBookServiceImpl(OwnedBookRepository ownedBookRepository) {
        this.ownedBookRepository = ownedBookRepository;
    }

    @Override
    public Set<OwnedBook> findAll() {
        return null;
    }

    @Override
    public OwnedBook findById(Long aLong) {
        return ownedBookRepository.findById(aLong).orElse(null);
    }

    @Override
    public OwnedBook save(OwnedBook object) {
        return ownedBookRepository.save(object);
    }

    @Override
    public void delete(OwnedBook object) {
        ownedBookRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        ownedBookRepository.deleteById(aLong);
    }

    @Override
    public OwnedBook findOwnedBookByBookAndUser(Book book, User user) {
        return ownedBookRepository.findOwnedBookByBookAndUser(book, user);
    }

}
