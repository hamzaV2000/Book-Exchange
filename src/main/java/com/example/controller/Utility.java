package com.example.controller;

import com.example.entity.User;
import com.example.services.UserService;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;

@Component
public class Utility {


    static String IP_ADDRESS ;
    private Utility (){

        URL whatismyip = null;
        try {
            whatismyip = new URL("https://api.ipify.org?format=json");
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            IP_ADDRESS = in.readLine(); //you get the IP as a String
            System.out.println(IP_ADDRESS);
            JSONObject jsonObject = new JSONObject(IP_ADDRESS);
            IP_ADDRESS = "http://" + jsonObject.getString("ip") + "/python";
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
