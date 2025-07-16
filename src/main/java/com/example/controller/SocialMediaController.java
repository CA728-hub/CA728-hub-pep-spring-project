package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

     @Autowired
    AccountService accountService;

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
        if (loggedIn != null) {
            return ResponseEntity.ok(loggedIn);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    
}
