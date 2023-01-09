package com.example.controller;

import com.example.demo.exception_handling.MyErrorResponse;
import com.example.entity.Book;
import com.example.entity.Review;
import com.example.entity.User;
import com.example.services.BookService;
import com.example.services.ReviewService;
import com.example.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;

import static com.example.controller.Utility.getUser;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final ReviewService reviewService;

    private final UserService userService;

    public BookController(BookService bookService, ReviewService reviewService, UserService userService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/getBook")
    private ResponseEntity<?> getBook(@RequestParam Long book_id){
        Book book = bookService.findById(book_id);
        if(book == null){
            return ResponseEntity.badRequest().body(new MyErrorResponse(400, "book is not found", LocalDate.now()));
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("/getMyRating")
    private ResponseEntity<?> getMyRating(@RequestParam Long book_id, Principal principal){
        User user = getUser(principal, userService);

        Book book = bookService.findById(book_id);
        if(book == null){
            return ResponseEntity.badRequest().body(new MyErrorResponse(400, "book is not found", LocalDate.now()));
        }
        Review review = reviewService.findReviewByBookAndUser(book, user);
        if(review != null)
            return ResponseEntity.ok("{ \"rating\": " + review.getUserRating() + "}");
        else{
            return ResponseEntity.ok("{ \"rating\": -1}");
        }
    }
}
