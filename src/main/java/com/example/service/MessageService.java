package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message postMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() >= 255) {
            return null;
        }

        if (!accountRepository.existsById(message.getPostedBy())) {
            return null;
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getMessagesByUserId(Integer userId) {
        if (!accountRepository.existsById(userId)) {
            return null;  
        }
        return messageRepository.findByPostedBy(userId);
    }

    public Message getMessageById(Integer id) {
        return messageRepository.findById(id).orElse(null);
    }

    public boolean deleteMessageById(Integer id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Message updateMessage(Message updatedMessage) {
        if (updatedMessage.getMessageText() == null || updatedMessage.getMessageText().isBlank()
                || updatedMessage.getMessageText().length() >= 255) {
            return null;
        }

        Optional<Message> messageOpt = messageRepository.findById(updatedMessage.getMessageId());
        if (messageOpt.isPresent()) {
            Message existing = messageOpt.get();
            existing.setMessageText(updatedMessage.getMessageText());
            return messageRepository.save(existing);
        }
        return null;
    }
}
