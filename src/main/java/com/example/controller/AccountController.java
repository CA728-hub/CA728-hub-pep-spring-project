package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public AccountController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account registered = accountService.register(account);
        if (registered != null) {
            return ResponseEntity.ok(registered);
        } else {
            return ResponseEntity.status(409).build(); 
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loggedIn = accountService.login(account.getUsername(), account.getPassword());
        return (loggedIn != null) ? ResponseEntity.ok(loggedIn) : ResponseEntity.status(401).build();
    }




    @GetMapping("/{userId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer userId) {
        List<Message> userMessages = messageService.getMessagesByUserId(userId);
        return (userMessages != null) ? ResponseEntity.ok(userMessages) : ResponseEntity.status(400).build();
    }


}
