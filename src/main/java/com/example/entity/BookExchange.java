package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"me_id", "my_book_id"})
})
public class BookExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JsonIgnore
    private User me;

    @ManyToOne
    @JsonIgnore
    private User him;

    @OneToOne
    private OwnedBook myBook;

    @OneToOne
    private OwnedBook hisBook;

    private ExchangeStatus status;

    public BookExchange() {
    }

    public BookExchange(User me, User him, OwnedBook myBook, OwnedBook hisBook, ExchangeStatus status) {
        this.me = me;
        this.him = him;
        this.myBook = myBook;
        this.hisBook = hisBook;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public User getHim() {
        return him;
    }

    public void setHim(User him) {
        this.him = him;
    }

    public OwnedBook getMyBook() {
        return myBook;
    }

    public void setMyBook(OwnedBook myBook) {
        this.myBook = myBook;
    }

    public OwnedBook getHisBook() {
        return hisBook;
    }

    public void setHisBook(OwnedBook hisBook) {
        this.hisBook = hisBook;
    }

    public ExchangeStatus getStatus() {
        return status;
    }

    public void setStatus(ExchangeStatus status) {
        this.status = status;
    }
}
