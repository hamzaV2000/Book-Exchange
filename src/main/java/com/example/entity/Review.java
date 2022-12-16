package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todoSeqGen")
    @SequenceGenerator(name = "todoSeqGen", sequenceName = "todoSeq", initialValue = 0)
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;

    @Lob
    private String reviewText;

    private LocalDate date;

    private byte userRating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte getUserRating() {
        return userRating;
    }

    public void setUserRating(byte userRating) {
        this.userRating = userRating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", book=" + book.toString() +
                ", user=" + user.toString() +
                ", reviewText='" + reviewText + '\'' +
                ", date=" + date +
                ", userRating=" + userRating +
                '}';
    }
}
