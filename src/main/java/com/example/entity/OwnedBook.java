package com.example.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"book_id", "user_id"})
})
public class OwnedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Book book;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private User user;
    private boolean available;
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean avaliable) {
        this.available = avaliable;
    }

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

    @Override
    public String toString() {
        return "OwnedBook{" +
                "book=" + book.getTitle() +
                "book_id=" + book.getId() +
                ", user=" + user.getUserName() +
                ", available=" + available +
                '}';
    }
}
