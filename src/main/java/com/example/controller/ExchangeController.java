package com.example.controller;


import com.example.crm.CrmExchange;
import com.example.demo.exception_handling.MyErrorResponse;
import com.example.entity.*;
import com.example.services.BookExchangeService;
import com.example.services.BookService;
import com.example.services.OwnedBookService;
import com.example.services.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.controller.Utility.*;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {


    private final OwnedBookService ownedBookService;
    private final UserService userService;
    private final BookExchangeService bookExchangeService;
    private final BookService bookService;

    public ExchangeController(OwnedBookService ownedBookService, UserService userService, BookExchangeService bookExchangeService, BookService bookService) {
        this.ownedBookService = ownedBookService;
        this.userService = userService;
        this.bookExchangeService = bookExchangeService;
        this.bookService = bookService;
    }

    @GetMapping("/booksForExchange")
    private ResponseEntity<?> booksForExchange(Principal principal) throws IOException {
        User user  = Utility.getUser(principal, userService);

        URL url = new URL(IP_ADDRESS + "/recommendBySimilarUsers/" + user.getId());

        String json = getResponseContent(url);
        List<CrmExchange> list = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        jsonArray.forEach(jsonh->{
            JSONObject jsonObject =(JSONObject) jsonh;

            Long user_id = jsonObject.getLong("user_id");
            Long book_id = jsonObject.getLong("book_id");
            System.out.println("user: " + user_id + ", book: " + book_id);
            User otherUser = userService.findById(user_id);
            Book book = bookService.findById(book_id);

            OwnedBook ownedBook = ownedBookService.findOwnedBookByBookAndUser(book, otherUser);

            //System.out.println(otherUser);
            if(ownedBook == null ){
                System.out.println("book is null");
                return;
            }
            if(!ownedBook.isAvailable()){
                System.out.println("book is not ava");
            }
            if(!otherUser.getCity().equalsIgnoreCase(user.getCity())) {
                System.out.println("cities are not equal");
                return;
            }
            CrmExchange crmExchange = new CrmExchange();

            crmExchange.setHis_book_id(ownedBook.getBook().getId());
            crmExchange.setHis_owned_book_id(ownedBook.getId());
            crmExchange.setHis_profile_image_url(otherUser.getProfileImageUrl());
            crmExchange.setHis_username(otherUser.getUserName());
            crmExchange.setHis_book_cover_image(ownedBook.getBook().getCoverPage());
            crmExchange.setHis_book_title(ownedBook.getBook().getTitle());

            list.add(crmExchange);

        });
        System.out.println(list);
        return ResponseEntity.ok(list);
    }
    @PostMapping("/initExchange")
    private ResponseEntity<?> initExchange(Principal principal,
                                           @RequestParam Long his_book_id,
                                           @RequestParam Long my_book_id){
        OwnedBook myBook = ownedBookService.findById(my_book_id);
        OwnedBook ownerBook = ownedBookService.findById(his_book_id);

        User user = userService.findByUserName(principal.getName());
        User him = userService.findById(ownerBook.getUser().getId());

        if(user.getId() != myBook.getUser().getId() || him.getId() != ownerBook.getUser().getId() || user.getId() == him.getId() || my_book_id == his_book_id)
            return ResponseEntity.badRequest().body(new MyErrorResponse(HttpStatus.BAD_REQUEST.value(),"initExchange Failed.", LocalDate.now()));


        BookExchange bookExchange = new BookExchange();

        bookExchange.setMyBook(myBook);
        bookExchange.setHisBook(ownerBook);

        bookExchange.setMe(user);
        bookExchange.setHim(him);

        bookExchange.setStatus(ExchangeStatus.ON_GOING);

        bookExchangeService.save(bookExchange);

        return ResponseEntity.ok(new MyErrorResponse(200, "init Exchange was successful", LocalDate.now()));
    }

    @GetMapping("/exchangesFromPeople")
    private ResponseEntity<?> getExchangesFromPeople(Principal principal){
        User user  = Utility.getUser(principal, userService);
        List<BookExchange> list =new ArrayList<>();
        bookExchangeService.findAllByHim(user).forEach(bookExchange -> {
            if(bookExchange.getStatus() == ExchangeStatus.ON_GOING)
                list.add(bookExchange);
        });

        return ResponseEntity.ok(list);
    }
    @GetMapping("/exchangesFromMe")
    private ResponseEntity<?> getExchangesFromMe(Principal principal){
        User user  = Utility.getUser(principal, userService);
        List<BookExchange> list = bookExchangeService.findAllByMe(user);

        return ResponseEntity.ok(list);
    }
    @PostMapping("/acceptExchange")
    private ResponseEntity<?> acceptExchange(Principal principal,
                                             @RequestParam Long exchange_id,
                                             @RequestParam Boolean accept){

        User user  = Utility.getUser(principal, userService);
        BookExchange bookExchange = bookExchangeService.findById(exchange_id);
        System.out.println(bookExchange);
        if(bookExchange.getHim().getId() != user.getId() || bookExchange.getHisBook().getUser().getId() != user.getId())
            return ResponseEntity.badRequest().body(new MyErrorResponse(400,"exchange request failed.", LocalDate.now()));

        if(accept){
            bookExchange.setStatus(ExchangeStatus.FINISHED);
            bookExchange.getHisBook().setUser(bookExchange.getMe());
            bookExchange.getMyBook().setUser(bookExchange.getHim());
            ownedBookService.save(bookExchange.getHisBook());
            ownedBookService.save(bookExchange.getMyBook());
            bookExchangeService.save(bookExchange);
            return ResponseEntity.ok(new MyErrorResponse(200, "exchange accepted successfully.", LocalDate.now()));
        }else{
            bookExchange.setStatus(ExchangeStatus.FAILED);
            bookExchangeService.save(bookExchange);
            return ResponseEntity.ok(new MyErrorResponse(200,"exchange rejected successfully.", LocalDate.now()));

        }
    }















}
