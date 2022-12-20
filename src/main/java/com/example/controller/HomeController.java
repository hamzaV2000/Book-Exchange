package com.example.controller;


import com.example.entity.User;
import com.example.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;

@RestController
@RequestMapping("")
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    private String home(){
        return "";
    }
    @GetMapping("/recommendBySimilarUsers")
    private String bySimilarUsers(Principal principal) throws IOException {
        String name = principal.getName();
        User user = userService.findByUserName(name);
        URL url = new URL("http://127.0.0.1:5001/recommendBySimilarUsers/" + user.getId());
        return getResponseContent(url);
    }

    @GetMapping("/favorites")
    private String favorites(Principal principal) throws IOException {
        String name = principal.getName();
        User user = userService.findByUserName(name);
        URL url = new URL("http://127.0.0.1:5001/userFavorites/" + user.getId());
        return getResponseContent(url);
    }



    private String getResponseContent(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
}
