package com.example.crm;

import com.example.entity.BookExchange;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class CrmMessage {

    private String userName;
    private String time;
    @NotNull
    private String message;
    @NotNull
    private Long bookExchange_id;

    public CrmMessage() {
    }

    public CrmMessage(String message, Long bookExchange_id) {
        this.message = message;
        this.bookExchange_id = bookExchange_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getBookExchange_id() {
        return bookExchange_id;
    }

    public void setBookExchange_id(Long bookExchange_id) {
        this.bookExchange_id = bookExchange_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
