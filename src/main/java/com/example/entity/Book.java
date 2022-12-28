package com.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Lob
    private String description;
    @Id
    private Long id;
    private String title;
    private float numOfPages;
    private String genres;
    private String author;

    private String publisher;
    private float publicationYear;


    private String coverPage;
    private String bookUrl;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    @JsonIgnore
    private Set<Review> reviewsSet;


    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Rating rating;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(float numOfPages) {
        this.numOfPages = numOfPages;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(float publicationYear) {
        this.publicationYear = publicationYear;
    }



    public String getCoverPage() {
        return coverPage;
    }

    public void setCoverPage(String coverPage) {
        this.coverPage = coverPage;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public Set<Review> getReviewsSet() {
        return reviewsSet;
    }

    public void setReviewsSet(Set<Review> reviewsSet) {
        this.reviewsSet = reviewsSet;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", numOfPages=" + numOfPages +
                ", genres='" + genres + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publicationYear=" + publicationYear +
                ", coverPage='" + coverPage + '\'' +
                ", bookUrl='" + bookUrl + '\'' +
                ", rating=" + rating +
                '}';
    }
}
