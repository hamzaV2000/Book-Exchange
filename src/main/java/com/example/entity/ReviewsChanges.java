package com.example.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "reviews_changes")
public class ReviewsChanges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
