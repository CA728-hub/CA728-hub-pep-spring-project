package com.example.controller;

import com.example.entity.Message;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        Message created = messageService.postMessage(message);
        return (created != null) ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
        Message message = messageService.getMessageById(id);
        return (message != null) ? ResponseEntity.ok(message) : ResponseEntity.ok().build();
    }


   @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer id) {
        boolean deleted = messageService.deleteMessageById(id);
        if (deleted) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok().build(); 
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Integer> patchMessage(@PathVariable Integer id, @RequestBody Message body) {
        String newText = body.getMessageText();

        if (newText == null || newText.isBlank() || newText.length() >= 255) {
            return ResponseEntity.status(400).build();
        }

        Message existing = messageService.getMessageById(id);
        if (existing == null) {
            return ResponseEntity.status(400).build();
        }

        existing.setMessageText(newText);
        messageService.updateMessage(existing);
        return ResponseEntity.ok(1);
    }
}
