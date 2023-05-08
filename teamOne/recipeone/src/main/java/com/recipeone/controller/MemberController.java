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
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login") //그냥 /login으로 요청왔을 때
    public void loginGET(String error, String logout) {
        log.info("login get...........");
        log.info("log out:" + logout);

        if (logout != null) {
            log.info("user logout.........");
        }
    }

    @PostMapping("/login")
    public String loginPost() {
        log.info("login 성공...........");

        return "redirect:/main2";
    }

    @GetMapping("/join") //그냥 /join으로 요청왔을 때
    public void joinGET() {
        log.info("join get...");
    }

    @PostMapping("/join") // 회원가입 폼 작성해서 내용 보낼 때
    public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        log.info("join post...");
        log.info(memberJoinDTO);

        try {
            memberService.join(memberJoinDTO); //MemberService의 join()메소드를 호출해서 회원가입 처리
            log.info("JOIN 단계1");
        } catch (MemberServiceImpl.MidExistException e) { //MemberService에서 MidExistException이 있을 경우(id가 중복되는 경우) error라는 속성에 user_id를 담아서 다시 /member/join경로로 redirect한다.
            log.info("JOIN 단계2");
            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:/member/join";
        }
        redirectAttributes.addFlashAttribute("result", "success");
        log.info("JOIN 단계3");
        //정상적으로 회원가입이 되었을 경우 result라는 속성에 success라는 값을 담아서 /member/login 경로로 redirect한다.
        //Flash Attribute를 이용해서 한번의 요청에만 해당 속성값이 전달된다.
        return "redirect:/member/login";

    }
}
