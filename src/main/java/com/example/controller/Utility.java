package com.example.controller;

import com.example.entity.User;
import com.example.services.UserService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;

@Component
public class Utility {



    private Utility (){
    }
    static User getUser(Principal principal, UserService userService) {
        String name = principal.getName();
        User user = userService.findByUserName(name);

        return user;
    }

    static String getResponseContent(URL url) throws IOException {
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
