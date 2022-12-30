package com.example.controller;


import com.example.demo.exception_handling.MyException;
import com.example.entity.Review;
import com.example.entity.User;
import com.example.services.BookService;
import com.example.services.ReviewService;
import com.example.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.example.controller.Utility.getResponseContent;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final String serverIP = "http://176.29.11.21/python";
    private User user = null;
    private final UserService userService;

    private final BookService bookService;

    private final ReviewService reviewService;
    private String top10 = null;

    private String bySimilarUsers = null;

    private String basedOnYourInterests = null;

    public HomeController(UserService userService, BookService bookService, ReviewService reviewService) {
        this.userService = userService;
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @GetMapping("")
    private String home(){
        return "";
    }

    @GetMapping("/basedOnYourInterests")
    private String basedOnYourInterests(Principal principal) throws IOException {
        if(basedOnYourInterests == null){
            if(user == null)
                user = Utility.getUser(principal, userService);

            HashSet<String> genres = new HashSet<>();
            List<Review> reviewList = reviewService.findAllByUser(user);
            if(reviewList.size() == 0){

                if(user.getInterest() != null){

                    URL url = new URL(serverIP + "/search/genre/" + user.getInterest());
                    basedOnYourInterests =  getResponseContent(url);
                }else{
                    throw new MyException("Specify Interests or Rate books");
                }
            }else{
                reviewList.forEach(review -> {
                    if(review.getUserRating() >= 4){
                        String[] genresArr = review.getBook().getGenres().replace("{", "").replace("}", "").split(",");
                        Arrays.stream(genresArr).toList().forEach(genre ->{
                            genres.add(genre);
                        });
                    }
                });
                URL url = new URL(serverIP + "/search/genre/" + genres.toString().replace("[", "").replace("]", ""));
                basedOnYourInterests =  getResponseContent(url);
            }
        }
        return basedOnYourInterests;
    }

    @GetMapping("/recommendBySimilarUsers")
    private String bySimilarUsers(Principal principal) throws IOException {
        if(bySimilarUsers == null) {
            if(user == null)
                user = Utility.getUser(principal, userService);

            URL url = new URL(serverIP + "/recommendBySimilarUsers/" + user.getId());
            bySimilarUsers =  getResponseContent(url);

        }
        return bySimilarUsers;
    }

    @GetMapping("/top10")
    private String getTop10() throws IOException {
        if(top10 == null){
            URL url = new URL(serverIP + "/topn");
            top10 = getResponseContent(url);
        }

        return top10;
    }








}
