package com.recipeone.controller;

import com.recipeone.dto.MemberJoinDTO;
import com.recipeone.dto.MemberMofifyDTO;
import com.recipeone.entity.Member;
import com.recipeone.repository.MemberRepository;
import com.recipeone.security.dto.MemberSecurityDTO;
import com.recipeone.service.MemberService;
import com.recipeone.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

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

    @PostMapping("/socialmodify")
    public String socialmodifyPost(MemberMofifyDTO memberMofifyDTO, RedirectAttributes redirectAttributes, @ModelAttribute Member member) {
        log.info("socialmodify post...");
        log.info(memberMofifyDTO);
        try {
            memberService.socialmodify(memberMofifyDTO, member); //MemberService의 join()메소드를 호출해서 회원가입 처리
        } catch (MemberServiceImpl.MidExistException e) { //MemberService에서 MidExistException이 있을 경우(id가 중복되는 경우) error라는 속성에 mid를 담아서 다시 /member/join경로로 redirect한다.
            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:/member/socialmodify";
        }catch (MemberService.UserEmailExistException e) {
            redirectAttributes.addFlashAttribute("error", "useremail");
            return "redirect:/member/socialmodify";
        }catch (MemberService.UserNickNameExistException e) {
            redirectAttributes.addFlashAttribute("error", "usernickname");
            return "redirect:/member/socialmodify";
        } catch (MemberService.ConfirmedPasswordException e) {
            redirectAttributes.addFlashAttribute("error", "confirmedPassword");
            return "redirect:/member/socialmodify";
        }  redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/";
    }

    @GetMapping("/socialmodify")
    public void modifyGET() {
        log.info("GET modify...........");
    }

    @GetMapping("/membermodify")
    public void membermodifyGET() {
        log.info("GET membermodify...........");
    }

    @PostMapping("/membermodify")
    public String membermodifyPost(MemberMofifyDTO memberMofifyDTO, RedirectAttributes redirectAttributes, @ModelAttribute Member member) {
        log.info("membermodify post...");
        log.info(memberMofifyDTO);
        try {
            memberService.membermodify(memberMofifyDTO, member);
        }catch (MemberService.UserEmailExistException e) {
            redirectAttributes.addFlashAttribute("error", "useremail");
            return "redirect:/member/membermodify";
        }catch (MemberService.UserNickNameExistException e) {
            redirectAttributes.addFlashAttribute("error", "usernickname");
            return "redirect:/member/membermodify";
        }  redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/mypage";
    }
  @GetMapping("/passwordmodify")
    public void passwordmodifyGET() {
        log.info("GET passwordmodify...........");
    }

  @PostMapping("/passwordmodify")
  public String passwordmodifyPost(MemberMofifyDTO memberMofifyDTO, RedirectAttributes redirectAttributes, @ModelAttribute Member member) {
      log.info("modify post...");
      log.info(memberMofifyDTO);
      try {
          memberService.passwordmodify(memberMofifyDTO, member);
      } catch (MemberService.WrongPasswordException e) {
          redirectAttributes.addFlashAttribute("error", "WrongPassword");
          return "redirect:/member/passwordmodify";
      }catch (MemberService.ConfirmedmodifyPasswordException e) {
          redirectAttributes.addFlashAttribute("error", "ConfirmedmodifyPassword");
          return "redirect:/member/passwordmodify";
      }  redirectAttributes.addFlashAttribute("result", "success");
      return "redirect:/member/mypage";
  }


    @GetMapping("/join") //그냥 /join으로 요청왔을 때
    public void joinGET() {
        log.info("join get...");
    }
    @GetMapping("/mypage")
    public void mypageGET() {
        log.info("mypage get...");
    }



    @PostMapping("/join") // 회원가입 폼 작성해서 내용 보낼 때
    public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        log.info("join post...");
        log.info(memberJoinDTO);

        try {
            memberService.join(memberJoinDTO); //MemberService의 join()메소드를 호출해서 회원가입 처리
        } catch (MemberServiceImpl.MidExistException e) { //MemberService에서 MidExistException이 있을 경우(id가 중복되는 경우) error라는 속성에 mid를 담아서 다시 /member/join경로로 redirect한다.
            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:/member/join";
        } catch (MemberServiceImpl.UserNickNameExistException e) { //MemberService에서 MidExistException이 있을 경우(id가 중복되는 경우) error라는 속성에 mid를 담아서 다시 /member/join경로로 redirect한다.
            redirectAttributes.addFlashAttribute("error", "usernickname");
            return "redirect:/member/join";
        }catch (MemberService.UserEmailExistException e) {
            redirectAttributes.addFlashAttribute("error", "useremail");
            return "redirect:/member/join";
        } catch (MemberService.ConfirmedPasswordException e) {
            redirectAttributes.addFlashAttribute("error", "confirmedPassword");
            return "redirect:/member/join";
        }
        redirectAttributes.addFlashAttribute("result", "success");
        //정상적으로 회원가입이 되었을 경우 result라는 속성에 success라는 값을 담아서 /member/login 경로로 redirect한다.
        //Flash Attribute를 이용해서 한번의 요청에만 해당 속성값이 전달된다.
        return "redirect:/member/login";

    }
}
