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
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException {
        log.info("여기까지 오긴하나");
        String user_id = memberJoinDTO.getUser_id();
        log.info("이건0");
        boolean exist = memberRepository.existsById(user_id);
        log.info("이건하나");
        log.info("이건2" + exist);
        log.info("이건3" + user_id);

        if (exist) {
            log.info("midexist 이거");
            throw new MidExistException();
        }
        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getUser_password()));
        member.addRole(MemberRole.USER);
        log.info("이건0");


        memberRepository.save(member);
//        log.info(member);
//        log.info(member.getRoleSet());
    }
}
