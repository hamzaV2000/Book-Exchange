package com.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

    private Integer rating_count;
    private Double average_rating;
    private Integer five_stars;
    private Integer four_stars;
    private Integer three_stars;
    private Integer two_stars;
    private Integer one_stars;
    private Integer zero_stars;

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

    public Integer getRating_count() {
        return rating_count;
    }

    public void setRating_count(Integer rating_count) {
        this.rating_count = rating_count;
    }

    public Double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(Double average_rating) {
        this.average_rating = average_rating;
    }

    public Integer getFive_stars() {
        return five_stars;
    }

    public void setFive_stars(Integer five_stars) {
        this.five_stars = five_stars;
    }

    public Integer getFour_stars() {
        return four_stars;
    }

    public void setFour_stars(Integer four_stars) {
        this.four_stars = four_stars;
    }

    public Integer getThree_stars() {
        return three_stars;
    }

    public void setThree_stars(Integer three_stars) {
        this.three_stars = three_stars;
    }

    public Integer getTwo_stars() {
        return two_stars;
    }

    public void setTwo_stars(Integer two_stars) {
        this.two_stars = two_stars;
    }

    public Integer getOne_stars() {
        return one_stars;
    }

    public void setOne_stars(Integer one_stars) {
        this.one_stars = one_stars;
    }

    public Integer getZero_stars() {
        return zero_stars;
    }

    public void setZero_stars(Integer zero_stars) {
        this.zero_stars = zero_stars;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rating_count=" + rating_count +
                ", average_rating=" + average_rating +
                ", five_stars=" + five_stars +
                ", four_stars=" + four_stars +
                ", three_stars=" + three_stars +
                ", two_stars=" + two_stars +
                ", one_stars=" + one_stars +
                ", zero_stars=" + zero_stars +
                '}';
    }

    public void addStar(byte rating) {
        switch (rating){
            case 0: setZero_stars(getZero_stars() + 1);break;
            case 1: setOne_stars(getOne_stars() + 1);break;
            case 2: setTwo_stars(getTwo_stars() + 1);break;
            case 3: setThree_stars(getThree_stars() + 1);break;
            case 4: setFour_stars(getFour_stars() + 1);break;
            case 5: setFive_stars(getFive_stars() + 1);break;
            default:
                System.out.println("invalid_rating");break;
        }
        setRating_count(getRating_count() + 1);
        calculateAverage();
    }
    public void removeStar(byte rating) {
        switch (rating){
            case 0: setZero_stars(getZero_stars() - 1);break;
            case 1: setOne_stars(getOne_stars() - 1);break;
            case 2: setTwo_stars(getTwo_stars() - 1);break;
            case 3: setThree_stars(getThree_stars() - 1);break;
            case 4: setFour_stars(getFour_stars() - 1);break;
            case 5: setFive_stars(getFive_stars() - 1);break;
            default:
                System.out.println("invalid_rating");break;
        }
        setRating_count(getRating_count() - 1);
        calculateAverage();

    }
    public void calculateAverage(){
        double sum = one_stars + (two_stars * 2) + (three_stars * 3) + (four_stars * 4) + (five_stars * 5);
        setAverage_rating(sum / rating_count);
    }
}
