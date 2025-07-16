package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

   public Account register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() || 
            account.getPassword() == null || account.getPassword().length() < 5) {
                System.out.println(" Invalid input for registration: " + account);
                return null;
        }



        Account existing = accountRepository.findByUsername(account.getUsername());
        if (existing != null) {
            System.out.println(" Username already exists: " + account.getUsername());
            return null;
        }

         Account saved = accountRepository.save(account);
         System.out.println("Registered account: " + saved);
         return saved;
    }





    public Account login(String username, String password) {
        if (username == null || username.isBlank() || 
            password == null || password.isBlank()) {
                return null;
        }



        Account found = accountRepository.findByUsername(username);
        if (found != null && found.getPassword().equals(password)) {
            return found;
        }
        return null;
    }


}
