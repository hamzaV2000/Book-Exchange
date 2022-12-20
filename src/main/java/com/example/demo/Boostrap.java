package com.example.demo;

import com.example.entity.User;
import com.example.repositories.BookRepository;
import com.example.repositories.ReviewRepository;
import com.example.repositories.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Component
@Transactional
public class Boostrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    private final ReviewRepository reviewRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Boostrap(UserRepository userRepository, BookRepository bookRepository, ReviewRepository reviewRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
/*
        try {
            PrintWriter rt = new PrintWriter(new FileWriter("TestUsers.txt"), true);
            for(Long i = 0l; i < 10 ;i++){
                User user = userRepository.findById(i).get();
                user.setPassword(bCryptPasswordEncoder.encode("raw12345"));
                rt.println(user.getUserName() + ", raw12345");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/
    }
}

