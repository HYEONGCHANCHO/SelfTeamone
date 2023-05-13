package com.recipeone.service;

import com.recipeone.dto.MemberJoinDTO;
import com.recipeone.dto.MemberMofifyDTO;
import com.recipeone.entity.Member;
import com.recipeone.entity.MemberRole;
import com.recipeone.repository.MemberRepository;
import com.recipeone.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException,UserNickNameExistException, UserEmailExistException,ConfirmedPasswordException { //문제없는 코드
        log.info("여기까지 오긴하나"); //문제없는 코드
        String mid = memberJoinDTO.getMid(); //문제없는 코드
        String usernickname = memberJoinDTO.getUsernickname(); //문제없는 코드
        String useremail = memberJoinDTO.getUseremail();
        if (memberRepository.findById(mid).isPresent()) {//문제없는 코드
            throw new MidExistException();//문제없는 코드
        } else if (memberRepository.findByUserNickName(usernickname).isPresent()) {//문제없는 코드
            throw new UserNickNameExistException();//문제없는 코드
        } else if (memberRepository.findByUseremail(useremail).isPresent()) {
            throw new UserEmailExistException();
        }else if (!memberJoinDTO.getPassword().equals(memberJoinDTO.getConfirmedPassword())) {
            throw new ConfirmedPasswordException();
        }
        Member member = modelMapper.map(memberJoinDTO, Member.class); //문제없는 코드

        member.changePassword(passwordEncoder.encode(memberJoinDTO.getPassword())); //문제없는 코드
        member.addRole(MemberRole.USER); //문제없는 코드
        member.addlev(1); //문제없는 코드
        member.loginFailCount(0);
        memberRepository.save(member); //문제없는 코드
        log.info(member.getRoleSet()); //문제없는 코드
    }
    @Override
    public void socialmodify(MemberMofifyDTO memberMofifyDTO, @ModelAttribute Member member ) throws MidExistException,UserNickNameExistException, UserEmailExistException,ConfirmedPasswordException { //문제없는 코드
        log.info("모디파이 오긴하나"); //문제없는 코드
        String mid = member.getMid(); //소셜 로그인으로 추가된 사용자로 현재 DB에 존재하는 이메일
        log.info(mid+"이거이거1");
        String password =memberMofifyDTO.getPassword();
        String usernickname =memberMofifyDTO.getUsernickname();
        String confirmedPassword =memberMofifyDTO.getConfirmedPassword();
        String oldusernickname = member.getUsernickname();
        log.info(password+"이거이거2");
        log.info(confirmedPassword+"이거이거3");
            if (!password.equals(confirmedPassword)) {
                log.info("모디파이 오긴하나2");
            throw new ConfirmedPasswordException();
        }  else if (!oldusernickname.equals(usernickname) && memberRepository.findByUserNickName(usernickname).isPresent()) {
                throw new UserNickNameExistException();
            }
        log.info("모디파이 오긴하나3");
            password =passwordEncoder.encode(password);
        memberRepository.updatePassword(password,mid);
        if (!oldusernickname.equals(usernickname)) { // 닉네임이 수정된 경우에만 업데이트
            memberRepository.updateusernickname(usernickname,mid);}
    }

    @Override
    public void membermodify(MemberMofifyDTO memberMofifyDTO, @ModelAttribute Member member) throws UserNickNameExistException, UserEmailExistException {
        String mid = member.getMid();
        String olduseremail = member.getUseremail();
        String usernickname =memberMofifyDTO.getUsernickname();
        String oldusernickname = member.getUsernickname(); // 기존 닉네임 정보 받아오기

        String useremail =memberMofifyDTO.getUseremail();
        String useraddr =memberMofifyDTO.getUseraddr();
        String userfullname =memberMofifyDTO.getUserfullname();
        String userphone =memberMofifyDTO.getUserphone();
        LocalDateTime now = LocalDateTime.now();
            log.info(mid+"1이거");
            log.info(olduseremail+"2");
            log.info(usernickname+"3");
            log.info(useremail+"4");
            log.info(useraddr+"5");
            log.info(userfullname+"5");
            log.info(userphone+"6");
        if (!olduseremail.equals(useremail)) {
            throw new UserEmailExistException();
        } else if (!oldusernickname.equals(usernickname) && memberRepository.findByUserNickName(usernickname).isPresent()) {
            throw new UserNickNameExistException();
        }

        memberRepository.updateuseremail(useremail,mid);
        if (!oldusernickname.equals(usernickname)) { // 닉네임이 수정된 경우에만 업데이트
            memberRepository.updateusernickname(usernickname,mid);}
        memberRepository.updateuseraddr(useraddr,mid);
        memberRepository.updateuserfullname(userfullname,mid);
        memberRepository.updateuserphone(userphone,mid);
        memberRepository.updatemoddate(now,mid);

        // memberSecurityDTO 업데이트
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memberSecurityDTO.setUsernickname(usernickname);
        memberSecurityDTO.setUseremail(useremail);
        memberSecurityDTO.setUseraddr(useraddr);
        memberSecurityDTO.setUserfullname(userfullname);
        memberSecurityDTO.setUserphone(userphone);
        log.info("여기5");

        // Authentication 객체 업데이트
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(memberSecurityDTO, memberSecurityDTO.getPassword(), memberSecurityDTO.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }


    @Override
    public void passwordmodify(MemberMofifyDTO memberMofifyDTO, @ModelAttribute Member member ) throws ConfirmedmodifyPasswordException,WrongPasswordException { //문제없는 코드
        String mid = member.getMid();
        String oldpassword = memberRepository.findPasswordByMid(mid);
        String oldinputpassword = memberMofifyDTO.getOldinputpassword();
        String newpassword =memberMofifyDTO.getNewpassword();
        String confirmedPassword =memberMofifyDTO.getConfirmedPassword();
        LocalDateTime moddate = LocalDateTime.now();
        log.info(oldpassword+"이거이거2");
        log.info(oldinputpassword+"이거이거33");
        log.info(newpassword+"이거이거3");
        log.info(confirmedPassword+"이거이거4");
        if(!passwordEncoder.matches(oldinputpassword,oldpassword)){ //입력한 값이 암호화된 값과 일치하는지 확인
            throw new WrongPasswordException();
        } else if (!newpassword.equals(confirmedPassword)) {
            throw new ConfirmedmodifyPasswordException();
        }
        newpassword =passwordEncoder.encode(newpassword);
        memberRepository.updatenewPassword(newpassword,mid);
        memberRepository.updatemoddate(moddate,mid);
    }


    // 아이디 중복 확인
    @Override
    public boolean checkDuplicatedUsername(String username) {
        return memberRepository.findById(username).isPresent();
    }
    @Override
    public boolean checkDuplicatedUsernickname(String usernickname) {
        return memberRepository.findByUserNickName(usernickname).isPresent();
    }
}
