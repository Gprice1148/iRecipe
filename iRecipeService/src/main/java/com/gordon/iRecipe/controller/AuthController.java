package com.gordon.iRecipe.controller;

import com.gordon.iRecipe.dto.RegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/signup")
    public void signup(@RequestBody RegisterRequest registerRequest)
}
