package com.recipeone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/admin")
    public void adminGet(){

    }
}
