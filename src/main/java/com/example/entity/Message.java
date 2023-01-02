package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private User sender;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private BookExchange bookExchange;

    @NotNull
    private Timestamp timestamp;

    @NotNull
    private String message;

    public Message() {
    }

    public Message(User sender, BookExchange bookExchange, Timestamp timestamp, String message) {
        this.sender = sender;
        this.bookExchange = bookExchange;
        this.timestamp = timestamp;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public BookExchange getBookExchange() {
        return bookExchange;
    }

    public void setBookExchange(BookExchange bookExchange) {
        this.bookExchange = bookExchange;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
