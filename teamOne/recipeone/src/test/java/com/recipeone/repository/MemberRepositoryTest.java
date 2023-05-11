package com.recipeone.repository;

import com.recipeone.entity.Member;
import com.recipeone.entity.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1,10).forEach(i -> {
            Member member = Member.builder()
                    .mid("member"+i)
                    .password(passwordEncoder.encode("1111"))
                    .useremail("email"+i+"@aaa.bbb")
                    .build();
            member.addRole(MemberRole.USER);

            if (i>=5){
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);


        });
        Optional<Member> result = memberRepository.getWithRoles("member5");

        Member member = result.orElseThrow();

        log.info(member);
        log.info(member.getRoleSet());

        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
    }



}