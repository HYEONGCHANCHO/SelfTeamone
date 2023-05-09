package com.recipeone.service;

import com.recipeone.dto.MemberJoinDTO;
import com.recipeone.entity.Member;
import com.recipeone.entity.MemberRole;
import com.recipeone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException { //문제없는 코드
        log.info("여기까지 오긴하나"); //문제없는 코드
        String mid = memberJoinDTO.getMid(); //문제없는 코드

        if (memberRepository.findById(mid).isPresent()) {//문제없는 코드
            throw new MidExistException();//문제없는 코드
        }
        Member member = modelMapper.map(memberJoinDTO, Member.class); //문제없는 코드

        member.changePassword(passwordEncoder.encode(memberJoinDTO.getPassword())); //문제없는 코드
        member.addRole(MemberRole.USER); //문제없는 코드
        memberRepository.save(member); //문제없는 코드
        log.info(member.getRoleSet()); //문제없는 코드
    }
}
