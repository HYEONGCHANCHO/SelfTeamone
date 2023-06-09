package com.recipeone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping(value = "/")
    public String main(){
        return "main";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/main2")
    public String main2(){
        return "main2";
    }


}
