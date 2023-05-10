package com.recipeone.controller;

import com.recipeone.dto.MemberJoinDTO;
import com.recipeone.service.MemberService;
import com.recipeone.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recipe")
@Log4j2
@RequiredArgsConstructor
public class RecipeController {

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'CHEF')")
    @GetMapping("/register")
    public String registerGET(){
        return "/recipe/register";
    }
    
    @PreAuthorize("principal.username==#boardDTO.writer") //작성자와 동일한 user만 수정 페이지 가능
    @GetMapping("/modify")
    public void modify(){

    }
}
