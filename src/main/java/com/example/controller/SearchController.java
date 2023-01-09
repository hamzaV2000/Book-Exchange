package com.example.controller;


import com.example.demo.exception_handling.MyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.controller.Utility.IP_ADDRESS;
import static com.example.controller.Utility.getResponseContent;

@RestController
@RequestMapping("/search")
public class SearchController {


    @GetMapping("")
    private ResponseEntity<?> bookSearch
            (@RequestParam String domain, @RequestParam String query)  {
        try {
            URL url = new URL(IP_ADDRESS + "/search/" + domain + "/" + query);
            String res = getResponseContent(url);
            if(res.contains("not found"))
                throw new MyException("not found");
            return ResponseEntity.ok(res);
        } catch (MalformedURLException e) {
            throw new MyException("Problem with Server");
        } catch (IOException e) {
            throw new MyException("Problem with Server");
        }

    }

}
