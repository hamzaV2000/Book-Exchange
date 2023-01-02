package com.example.controller;


import com.example.entity.*;
import com.example.services.BookExchangeService;
import com.example.services.BookService;
import com.example.services.OwnedBookService;
import com.example.services.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.controller.Utility.getResponseContent;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private final String serverIP = "http://176.29.9.132/python";
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
    private Map<Long, Long> booksForExchange(Principal principal) throws IOException {
        User user  = Utility.getUser(principal, userService);

        URL url = new URL(serverIP + "/recommendBySimilarUsers/" + user.getId());

        String json = getResponseContent(url);
        Map<Long, Long> map = new HashMap<>();
        JSONArray jsonArray = new JSONArray(json);
        jsonArray.forEach(jsonh->{
            JSONObject jsonObject =(JSONObject) jsonh;

            Long user_id = jsonObject.getLong("user_id");
            Long book_id = jsonObject.getLong("book_id");
            System.out.println("user: " + user_id + ", book: " + book_id);
            User otherUser = userService.findById(user_id);
            Book book = bookService.findById(book_id);

            OwnedBook ownedBook = ownedBookService.findOwnedBookByBookAndUser(book, otherUser);

            if(ownedBook == null || !ownedBook.isAvaliable())
                return;
            if(!otherUser.getCity().equals(user.getCity()))
                return;
            map.put(otherUser.getId(), ownedBook.getId());

        });

        return map;
    }
    @PostMapping("/initExchange")
    private String initExchange(Principal principal, @RequestParam Long his_book_id, @RequestParam Long my_book_id){
        OwnedBook myBook = ownedBookService.findById(my_book_id);
        OwnedBook ownerBook = ownedBookService.findById(his_book_id);

        User user = userService.findByUserName(principal.getName());
        User him = userService.findById(ownerBook.getUser().getId());

        if(user.getId() != myBook.getUser().getId() || him.getId() != ownerBook.getUser().getId() || user.getId() == him.getId() || my_book_id == his_book_id)
            return "failed";


        BookExchange bookExchange = new BookExchange();

        bookExchange.setMyBook(myBook);
        bookExchange.setHisBook(ownerBook);

        bookExchange.setMe(user);
        bookExchange.setHim(him);

        bookExchange.setStatus(ExchangeStatus.ON_GOING);

        bookExchangeService.save(bookExchange);

        return "success";
    }

    @GetMapping("/exchangesFromPeople")
    private List<BookExchange> getExchangesFromPeople(Principal principal){
        User user  = Utility.getUser(principal, userService);
        List<BookExchange> list =new ArrayList<>();
        bookExchangeService.findAllByHim(user).forEach(bookExchange -> {
            if(bookExchange.getStatus() == ExchangeStatus.ON_GOING)
                list.add(bookExchange);
        });

        return list;
    }
    @GetMapping("/exchangesFromMe")
    private List<BookExchange> getExchangesFromMe(Principal principal){
        User user  = Utility.getUser(principal, userService);
        List<BookExchange> list = bookExchangeService.findAllByMe(user);

        return list;
    }
    @PostMapping("/acceptExchange")
    private String acceptExchange(Principal principal, @RequestParam Long exchange_id, @RequestParam Boolean accept){

        User user  = Utility.getUser(principal, userService);
        BookExchange bookExchange = bookExchangeService.findById(exchange_id);
        System.out.println(bookExchange);
        if(bookExchange.getHim().getId() != user.getId() || bookExchange.getHisBook().getUser().getId() != user.getId())
            return "failed";

        if(accept){
            bookExchange.setStatus(ExchangeStatus.FINISHED);
            bookExchange.getHisBook().setUser(bookExchange.getMe());
            bookExchange.getMyBook().setUser(bookExchange.getHim());
            ownedBookService.save(bookExchange.getHisBook());
            ownedBookService.save(bookExchange.getMyBook());
        }else{
            bookExchange.setStatus(ExchangeStatus.FAILED);
        }

        bookExchangeService.save(bookExchange);
        return "exchange: " + (accept ? "accepted" : "rejected") + ", successfully.";
    }















}
