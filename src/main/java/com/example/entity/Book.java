package com.example.entity;




import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;


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

    private float book_average_rating;
    private String coverPage;
    private String bookUrl;
    private Integer ratingCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private Set<Review> reviewsSet;


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

    public float getBook_average_rating() {
        return book_average_rating;
    }

    public void setBook_average_rating(float book_average_rating) {
        this.book_average_rating = book_average_rating;
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

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                '}';
    }
}
