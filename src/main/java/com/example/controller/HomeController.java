package com.example.controller;


import com.example.demo.exception_handling.MyException;
import com.example.entity.*;
import com.example.services.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.*;

import static com.example.controller.Utility.IP_ADDRESS;
import static com.example.controller.Utility.getResponseContent;


@RestController
@RequestMapping("/home")
public class HomeController {

    private User user = null;
    private final UserService userService;
    private final ReviewService reviewService;

    private final BookService bookService;

    public HomeController(UserService userService, ReviewService reviewService, BookService bookService) {
        this.userService = userService;
        this.reviewService = reviewService;
        this.bookService = bookService;
    }

    @GetMapping("")
    private String home(){
        return "";
    }

    @GetMapping("/basedOnYourInterests")
    private ResponseEntity<?> basedOnYourInterests(Principal principal) throws IOException {


        user = Utility.getUser(principal, userService);
        HashSet<String> genres = new HashSet<>();
        List<Review> reviewList = reviewService.findAllByUser(user);

        if(reviewList.size() != 0){
            reviewList.forEach(review -> {
                if(review.getUserRating() >= 4){
                    String[] genresArr = review.getBook().getGenres().replace("{", "").replace("}", "").split(",");
                    Arrays.stream(genresArr).toList().forEach(genre ->{
                        genres.add(genre);
                    });
                }
            });
            if (genres.size() != 0){
                String url = IP_ADDRESS + "/search/genre/" + genres.toString().replace("[", "").replace("]", "").replace(" ", "%20");
                return ResponseEntity.ok(getBooksFromUrl(url));
            }
        }
        if(user.getInterest() != null){
            String url = IP_ADDRESS + "/search/genre/" + user.getInterest().replace(" ", "%20");
            return ResponseEntity.ok(getBooksFromUrl(url));
        }else{
            throw new MyException("Specify Interests or Rate books");
        }

    }

    @GetMapping("/recommendBySimilarUsers")
    public ResponseEntity<?> bySimilarUsers(Principal principal) throws IOException {
        user = Utility.getUser(principal, userService);
        URL url = new URL(IP_ADDRESS + "/recommendBySimilarUsers/" + user.getId());
        String res = getResponseContent(url);
        JSONArray array = new JSONArray(res);
        List<Book> list = new ArrayList<>();
        array.forEach(jsonObject->{
            JSONObject json = (JSONObject) jsonObject;
            Long id = json.getLong("book_id");
            Book book = bookService.findById(id);
            list.add(book);
        });
        return ResponseEntity.ok(list);
    }

    @GetMapping("/top10")
    private ResponseEntity<?> getTop10() throws IOException {

        return ResponseEntity.ok(getBooksFromUrl(IP_ADDRESS + "/topn"));
    }

    @GetMapping("/recommendBySimilarBook")
    private ResponseEntity<?> bySimilarBook(@RequestParam Long book_id) throws IOException {
        List<Book> list = getBooksFromUrl(IP_ADDRESS + "/recommendBySimilarBook/" + book_id);
        return ResponseEntity.ok(list.subList(1, list.size()));
    }
    private List<Book> getBooksFromUrl(String surl) throws IOException {
        URL url = new URL(surl);
        List<Book> list = new ArrayList<>();
        String res[] = getResponseContent(url).replace("[", "").replace("]","").split(",");
        Arrays.stream(res).forEach(str->{
            Long id = Double.valueOf(str).longValue();
            Book book = bookService.findById(id);
            list.add(book);
        });
        return list;
    }








}
