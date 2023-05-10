package com.recipeone.service;

import com.recipeone.dto.MemberJoinDTO;
import com.recipeone.dto.MemberMofifyDTO;
import com.recipeone.entity.Member;
import com.recipeone.entity.MemberRole;
import com.recipeone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException, UserEmailExistException,ConfirmedPasswordException { //문제없는 코드
        log.info("여기까지 오긴하나"); //문제없는 코드
        String mid = memberJoinDTO.getMid(); //문제없는 코드
        String useremail = memberJoinDTO.getUseremail();
        if (memberRepository.findById(mid).isPresent()) {//문제없는 코드
            throw new MidExistException();//문제없는 코드
        } else if (memberRepository.findByUseremail(useremail).isPresent()) {
            throw new UserEmailExistException();
        }else if (!memberJoinDTO.getPassword().equals(memberJoinDTO.getConfirmedPassword())) {
            throw new ConfirmedPasswordException();
        }
        Member member = modelMapper.map(memberJoinDTO, Member.class); //문제없는 코드

        member.changePassword(passwordEncoder.encode(memberJoinDTO.getPassword())); //문제없는 코드
        member.addRole(MemberRole.USER); //문제없는 코드
        member.addlev(1); //문제없는 코드
        memberRepository.save(member); //문제없는 코드
        log.info(member.getRoleSet()); //문제없는 코드
    }
    @Override
    public void modify(MemberMofifyDTO memberMofifyDTO, @ModelAttribute Member member ) throws MidExistException, UserEmailExistException,ConfirmedPasswordException { //문제없는 코드
        log.info("모디파이 오긴하나"); //문제없는 코드
//        member = modelMapper.map(memberMofifyDTO, Member.class); //문제없는 코드
        String mid = member.getMid(); //소셜 로그인으로 추가된 사용자로 현재 DB에 존재하는 이메일
        log.info(mid+"이거이거1");
//        String useremail = member.getUseremail();
        String password =memberMofifyDTO.getPassword();
        String confirmedPassword =memberMofifyDTO.getConfirmedPassword();
        log.info(password+"이거이거2");
        log.info(confirmedPassword+"이거이거3");
//        log.info(Confirmedpassword+"이거이거");

//        log.info(mid,password+"여기");
//        if (memberRepository.findById(mid).isPresent()) {//문제없는 코드
//            throw new MidExistException();//문제없는 코드
//        } else if (memberRepository.findByUseremail(useremail).isPresent()) {
//            throw new UserEmailExistException();
//        }else
            if (!password.equals(confirmedPassword)) {
                log.info("모디파이 오긴하나2");
            throw new ConfirmedPasswordException();
        }
        log.info("모디파이 오긴하나3");
            password =passwordEncoder.encode(password);
        memberRepository.updatePassword(password,mid);
    }
}
