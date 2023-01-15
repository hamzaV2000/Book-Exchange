package com.example.controller;

import com.example.crm.CrmUser;
import com.example.demo.exception_handling.MyErrorResponse;
import com.example.entity.*;
import com.example.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    private final UserTokenService userTokenService;


    public UserPageController(UserService userService, OwnedBookService ownedBookService, BookService bookService, ReviewService reviewService, ReviewChangesService reviewChangesService, UserTokenService userTokenService) {
        this.userService = userService;
        this.ownedBookService = ownedBookService;
        this.bookService = bookService;
        this.reviewService = reviewService;
        this.reviewChangesService = reviewChangesService;
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
    private ResponseEntity<?> editUserInfo(@RequestBody CrmUser s, Principal principal){
        User newUser = getUser(principal, userService);
        UserToken userToken = userTokenService.findUserTokenByUserName(newUser.getUserName());
        System.out.println("before checking email ");
        String emailExist = null;
        String usernameExist = null;
        if(s.getEmail() != null && userService.findByEmail(s.getEmail()) != null && !s.getEmail().equals(newUser.getEmail()))
            emailExist = "Email already exists";

        if(s.getUserName() != null && userService.findByUserName(s.getUserName()) != null && !s.getUserName().equals(newUser.getUserName()))
            usernameExist = "username already exists";

        if(emailExist != null || usernameExist != null){

            StringBuilder sb = new StringBuilder();

            if(emailExist != null)
                sb.append(emailExist).append(',');
            if(usernameExist != null)
                sb.append(usernameExist).append(',');
            System.out.println("invalid checking email ");
            return ResponseEntity.badRequest().body(new MyErrorResponse(400, sb.substring(0, sb.length() - 1), LocalDate.now()));
        }

        if(s.getPassword() != null){
            if(s.getMatchingPassword() == null || !s.getMatchingPassword().equals(s.getPassword())){
                return ResponseEntity.badRequest().body(new MyErrorResponse(400,"Passwords don't match." , LocalDate.now()));
            }
        }

        System.out.println("edit success ");
        userService.save(s, newUser);
        userTokenService.delete(userToken);
        return ResponseEntity.ok(new MyErrorResponse(200, "Edited Successfully.", LocalDate.now()));
    }

    @GetMapping("/favorites")
    private ResponseEntity<?> getFavorites(Principal principal) throws IOException {

        user = getUser(principal, userService);
        //System.out.printf(IP_ADDRESS + "/userFavorites/" + user.getId());
        return ResponseEntity.ok(getBooksFromUrl(IP_ADDRESS + "/userFavorites/" + user.getId()));
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
    @PostMapping("/removeBookFromOwned")
    private ResponseEntity<?> removeBookFromOwned(@RequestParam Long book_id, Principal principal){
        user = getUser(principal, userService);
        Book book = bookService.findById(book_id);
        if(book == null)
            return ResponseEntity.badRequest().body(new MyErrorResponse(400, "failed to find book.", LocalDate.now()));

        OwnedBook ownedBook = ownedBookService.findOwnedBookByBookAndUser(book, user);

        if(ownedBook == null)
            return ResponseEntity.badRequest().body(new MyErrorResponse(400, "You don't own this book.", LocalDate.now()));

        ownedBookService.deleteById(ownedBook.getId());
        for(OwnedBook b : user.getOwnedBookSet()){
            if(b.getId().longValue() == ownedBook.getId().longValue()){
                ownedBook = b;
                break;
            }
        }
        user.getOwnedBookSet().remove(ownedBook);
        ownedBookService.deleteById(ownedBook.getId());
        return ResponseEntity.ok(new MyErrorResponse(200, "removed successfully", LocalDate.now()));

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

        OwnedBook book = ownedBookService.findById(book_id);
        if(book == null || (book.getUser().getId().longValue() != user.getId().longValue()))
            return ResponseEntity.badRequest().body(new MyErrorResponse(400, "you don't own this book", LocalDate.now()));

        book.setAvaliable(available);
        ownedBookService.save(book);

        return ResponseEntity.ok(new MyErrorResponse(200, available ? "Your book is available" : "Your book is unavailable", LocalDate.now()));
    }
    private List<Book> getBooksFromUrl(String sUrl) throws IOException {
        URL url = new URL(sUrl);
        List<Book> list = new ArrayList<>();
        String[] res = getResponseContent(url).replace("[", "").replace("]","").split(",");
        Arrays.stream(res).forEach(str->{
            Long id = Double.valueOf(str).longValue();
            Book book = bookService.findById(id);
            list.add(book);
        });
        return list;
    }
}
