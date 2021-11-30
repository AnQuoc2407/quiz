package com.api.quiz.controller;

import com.api.quiz.model.User;
import com.api.quiz.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {
    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

}
