package com.example.demo;

import com.example.repositories.BookRepository;
import com.example.repositories.ReviewRepository;
import com.example.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

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
//        Book = bookRepository.findById(36l).orElse(null);
//
//        if(book != null)
//            System.out.println(book);

/*
        try {
            URL whatismyip = new URL("https://api.ipify.org?format=json");
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine(); //you get the IP as a String
            System.out.println(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/




    }
}

