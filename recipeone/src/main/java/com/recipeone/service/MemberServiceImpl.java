package com.recipeone.service;

import com.recipeone.dto.MemberJoinDTO;
import com.recipeone.entity.Member;
import com.recipeone.entity.MemberRole;
import com.recipeone.repository.MemberRepostiry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final ModelMapper modelMapper;
    private final MemberRepostiry memberRepostiry;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException{
        String user_id = memberJoinDTO.getUser_id();
        boolean exist = memberRepostiry.existsById(user_id);

        if (exist){
            throw new MidExistException();
        }
        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getUser_password()));
        member.addRole(MemberRole.USER);

        log.info("======================");
        log.info(member);
        log.info(member.getRoleSet());

        memberRepostiry.save(member);
    }
}
