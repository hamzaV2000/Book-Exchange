package com.example.controller;

import com.example.crm.CrmMessage;
import com.example.demo.exception_handling.MyErrorResponse;
import com.example.demo.exception_handling.MyException;
import com.example.entity.BookExchange;
import com.example.entity.ExchangeStatus;
import com.example.entity.Message;
import com.example.entity.User;
import com.example.services.BookExchangeService;
import com.example.services.MessageService;
import com.example.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final UserService userService;
    private final MessageService messageService;

    private final BookExchangeService bookExchangeService;

    public MessageController(UserService userService, MessageService messageService, BookExchangeService bookExchangeService) {
        this.userService = userService;
        this.messageService = messageService;
        this.bookExchangeService = bookExchangeService;
    }

    @PostMapping("/send")
    private ResponseEntity<?> sendMessage(@Valid @RequestBody CrmMessage crmMessage, Principal principal){
        User user  = Utility.getUser(principal, userService);

        BookExchange bookExchange = bookExchangeService.findById(crmMessage.getBookExchange_id());

        if((user.getId() != bookExchange.getHim().getId() && user.getId() != bookExchange.getMe().getId()) || bookExchange.getStatus() != ExchangeStatus.ON_GOING)
            throw new MyException("You don't have access to this conversation.");

        Message message = new Message();
        message.setSender(user);
        message.setMessage(crmMessage.getMessage());
        message.setBookExchange(bookExchange);
        message.setTimestamp(new Timestamp(System.currentTimeMillis()));

        messageService.save(message);

        return ResponseEntity.ok(new MyErrorResponse(200, "sent successfully", LocalDate.now()));
    }

    @GetMapping("/conversation")
    private ResponseEntity<?> conversation(Principal principal, @RequestParam Long exchange_id){
        User user  = Utility.getUser(principal, userService);
        BookExchange bookExchange = bookExchangeService.findById(exchange_id);

        if((user.getId() != bookExchange.getHisBook().getUser().getId() && user.getId() != bookExchange.getMe().getId()) || bookExchange.getStatus() != ExchangeStatus.ON_GOING)
            throw new MyException("You don't have access to this conversation.");

        List<Message> messages = messageService.findAllByBookExchange(bookExchange);
        Collections.sort(messages, Comparator.comparing(Message::getTimestamp));

        List<CrmMessage> list = new ArrayList<>();
        messages.forEach(message -> {

            CrmMessage crmMessage = new CrmMessage();

            crmMessage.setUserName(message.getSender().getUserName());
            crmMessage.setMessage(message.getMessage());
            crmMessage.setBookExchange_id(bookExchange.getId());
            crmMessage.setTime(message.getTimestamp().toString());

            list.add(crmMessage);
        });
        return ResponseEntity.ok(list);
    }
}
