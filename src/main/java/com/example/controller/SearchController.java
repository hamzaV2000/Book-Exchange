package com.example.controller;


import com.example.demo.exception_handling.MyException;
import com.example.entity.Book;
import com.example.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.controller.Utility.IP_ADDRESS;
import static com.example.controller.Utility.getResponseContent;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final BookService bookService;

    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    private ResponseEntity<?> bookSearch
            (@RequestParam String domain, @RequestParam String query)  {
        try {
            String url = IP_ADDRESS + ("/search/" + domain + "/" + query).replace(" ", "%20");

            return ResponseEntity.ok(getBooksFromUrl(url));
        } catch (MalformedURLException e) {
            throw new MyException("Problem with Server");
        } catch (IOException e) {
            throw new MyException("Problem with Server");
        }

    }
    private List<Book> getBooksFromUrl(String surl) throws IOException {
        URL url = new URL(surl);
        List<Book> list = new ArrayList<>();
        String result = getResponseContent(url);
        if(result.contains("not found"))
            throw new MyException("not found");
        String res[] = result.replace("[", "").replace("]","").split(",");
        Arrays.stream(res).forEach(str->{
            Long id = Double.valueOf(str).longValue();
            Book book = bookService.findById(id);
            list.add(book);
        });
        return list;
    }

}
