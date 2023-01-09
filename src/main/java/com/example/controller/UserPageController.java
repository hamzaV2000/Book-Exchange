package com.example.controller;

import com.example.crm.CrmUser;
import com.example.demo.exception_handling.MyErrorResponse;
import com.example.entity.*;
import com.example.services.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;

import static com.example.controller.Utility.*;

@RestController
@RequestMapping("/profile")
public class UserPageController {

    private User user = null;

    private final UserService userService;
    private final OwnedBookService ownedBookService;
    private final BookService bookService;
    private final ReviewService reviewService;
    private final ReviewChangesService reviewChangesService;

    private final RatingService ratingService;

    private final UserTokenService userTokenService;


    public UserPageController(UserService userService, OwnedBookService ownedBookService, BookService bookService, ReviewService reviewService, ReviewChangesService reviewChangesService, RatingService ratingService, UserTokenService userTokenService) {
        this.userService = userService;
        this.ownedBookService = ownedBookService;
        this.bookService = bookService;
        this.reviewService = reviewService;
        this.reviewChangesService = reviewChangesService;
        this.ratingService = ratingService;

        this.userTokenService = userTokenService;
    }

    @GetMapping("/user")
    private ResponseEntity<User> getUserInfo(Principal principal){

        User newUser = getUser(principal, userService);
        newUser.setPassword(null);
        newUser.setReviewSet(null);
        newUser.setOwnedBookSet(null);


        return ResponseEntity.ok().body(newUser);

    }

    @PutMapping("/editUser")
    private ResponseEntity<?> editUserInfo(@Valid @RequestBody CrmUser s, BindingResult bindingResult, Principal principal){
        User newUser = getUser(principal, userService);
        UserToken userToken = userTokenService.findUserTokenByUserName(newUser.getUserName());

        String emailExist = null;
        String usernameExist = null;
        if(s.getEmail() != null && userService.findByEmail(s.getEmail()) != null && !s.getEmail().equals(newUser.getEmail()))
            emailExist = "Email already exists";

        if(s.getUserName() != null && userService.findByUserName(s.getUserName()) != null && !s.getUserName().equals(newUser.getUserName()))
            usernameExist = "username already exists";

        StringBuilder sb = new StringBuilder();

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> sb.append(objectError.getDefaultMessage()+','));
        }

        if(emailExist != null)
            sb.append(emailExist + ',');
        if(usernameExist != null)
            sb.append(usernameExist + ',');


        if(!sb.toString().equals(""))
            return ResponseEntity.badRequest().body(new MyErrorResponse(400, sb.toString().substring(0, sb.length() - 1), LocalDate.now()));

        userService.save(s, newUser);
        userTokenService.delete(userToken);
        return ResponseEntity.ok(new MyErrorResponse(200, "Edited Successfully.", LocalDate.now()));
    }

    @GetMapping("/favorites")
    private ResponseEntity<?> getFavorites(Principal principal) throws IOException {

        user = getUser(principal, userService);
        System.out.printf(IP_ADDRESS + "/userFavorites/" + user.getId());
        URL url = new URL(IP_ADDRESS + "/userFavorites/" + user.getId());

        return ResponseEntity.ok(getResponseContent(url));
    }


    @GetMapping("/owned")
    private ResponseEntity<?> getOwned(Principal principal){

        user = getUser(principal, userService);

        System.out.println(user.getUserName());

        user.getOwnedBookSet().forEach(book ->{
            book.setUser(null);
            book.getBook().getRating().setBook(null);
        });

        return  ResponseEntity.ok(user.getOwnedBookSet());
    }


    @ResponseBody
    @PostMapping("/addBookToOwned")
    private ResponseEntity<?> addBookToOwned(@RequestParam Long book_id, Principal principal){

        user = getUser(principal, userService);

        Book book = bookService.findById(book_id);
        if(book == null)
            return ResponseEntity.badRequest().body(new MyErrorResponse(400, "failed to add book", LocalDate.now()));

        OwnedBook ownedBook = new OwnedBook();
        ownedBook.setBook(book);
        ownedBook.setUser(user);
        ownedBook.setAvaliable(false);
        ownedBookService.save(ownedBook);
        user.getOwnedBookSet().add(ownedBook);
        return ResponseEntity.ok(new MyErrorResponse(200, "added successfully", LocalDate.now()));
    }


    @ResponseBody
    @PostMapping("/rateBook")
    private ResponseEntity<?> rateBook
            (@RequestParam Long book_id, @RequestParam byte rating, Principal principal){

        user = getUser(principal, userService);

        Book book = bookService.findById(book_id);
        if(book == null)
            return ResponseEntity.badRequest().body(new MyErrorResponse(400, "failed to rate book", LocalDate.now()));


        Review review = new Review();
        review.setBook(book);
        review.setUserRating(rating);
        review.setUser(user);
        review.setTimestamp(new Timestamp(System.currentTimeMillis()));

        ReviewChanges reviewChanges = new ReviewChanges();
        reviewChanges.setUser(user);

        reviewChangesService.save(reviewChanges);

        reviewService.save(review);


        return ResponseEntity.ok(new MyErrorResponse(200, "rated successful", LocalDate.now()));
    }


    @ResponseBody
    @PostMapping("/makeBookAvailable")
    private ResponseEntity<?> makeBookAvailable
            (@RequestParam Long book_id , @RequestParam Boolean available, Principal principal){

        user = getUser(principal, userService);

        OwnedBook book = ownedBookService.findOwnedBookByBookAndUser(bookService.findById(book_id), user);
        if(book == null)
            return ResponseEntity.badRequest().body(new MyErrorResponse(400, "you don't own this book", LocalDate.now()));

        book.setAvaliable(available);
        ownedBookService.save(book);

        return ResponseEntity.ok(new MyErrorResponse(200, available ? "your book is available" : "your book is unavailable", LocalDate.now()));
    }
}
