package com.example.controller;


import com.example.demo.exception_handling.MyException;
import com.example.entity.*;
import com.example.services.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.*;

import static com.example.controller.Utility.getResponseContent;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final String serverIP = "http://176.29.9.132/python";
    private User user = null;
    private final UserService userService;



    private final ReviewService reviewService;




    public HomeController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("")
    private String home(){
        return "";
    }

    @GetMapping("/basedOnYourInterests")
    private String basedOnYourInterests(Principal principal) throws IOException {

        user = Utility.getUser(principal, userService);

        HashSet<String> genres = new HashSet<>();
        List<Review> reviewList = reviewService.findAllByUser(user);
        if(reviewList.size() == 0){

            if(user.getInterest() != null){

                URL url = new URL(serverIP + "/search/genre/" + user.getInterest());
                return getResponseContent(url);
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
            return getResponseContent(url);
        }
    }

    @GetMapping("/recommendBySimilarUsers")
    public String bySimilarUsers(Principal principal) throws IOException {


        user = Utility.getUser(principal, userService);

        URL url = new URL(serverIP + "/recommendBySimilarUsers/" + user.getId());
        return getResponseContent(url);
    }

    @GetMapping("/top10")
    private String getTop10() throws IOException {

        URL url = new URL(serverIP + "/topn");

        return getResponseContent(url);
    }








}
