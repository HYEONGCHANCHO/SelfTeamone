package com.recipeone.controller;

import com.recipeone.dto.MemberJoinDTO;
import com.recipeone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public void loginGet(String error, String logout){
        log.info("login get...........");
        log.info("log out:" + logout);

        if (logout !=null){
            log.info("user logout.........");
        }
    }

    @GetMapping("/join")
    public void joinGET(){
        log.info("join get...");
    }

    @PostMapping("/join")
    public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes){
        log.info("join post...");
        log.info(memberJoinDTO);

        try{
            memberService.join(memberJoinDTO);
        } catch (MemberService.MidExistException e){
            redirectAttributes.addFlashAttribute("error", "user_id");
            return "redirect:/member/join";
        }
        redirectAttributes.addFlashAttribute("result","success");

        return "redirect:/member/login";

    }
}