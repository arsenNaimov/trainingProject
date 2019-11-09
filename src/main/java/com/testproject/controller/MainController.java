package com.testproject.controller;

import com.testproject.domain.Message;
import com.testproject.domain.User;
import com.testproject.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
    public class MainController {

    @Autowired
    private MessageRepository messageRepository;


    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map <String, Object> model){
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map <String, Object> model){
        Message message = new Message();
        message.setText(text);
        message.setTag(tag);
        message.setAuthor(user);
        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map <String, Object> model){
        Iterable <Message> messages;
        if(filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);;
        }else {
            messages = messageRepository.findAll();
        }
        model.put("messages", messages);
        return "main";
    }

}
