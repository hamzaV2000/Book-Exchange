package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    private Long id;

    @ManyToOne
    @NotNull
    private Book book;
    @ManyToOne
    @NotNull
    private User user;
    @NotNull
    private byte userRating;

    @NotNull
    private Timestamp timestamp;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public byte getUserRating() {
        return userRating;
    }

    public void setUserRating(byte userRating) {
        this.userRating = userRating;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
