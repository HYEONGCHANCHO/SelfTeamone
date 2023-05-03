package com.recipeone.repository;

import com.recipeone.entity.Member;
import com.recipeone.entity.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberRepostiryTest {

    @Autowired
    private MemberRepostiry memberRepostiry;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .user_id("member"+i)
                    .user_password(passwordEncoder.encode("1111"))
                    .build();
            member.addRole(MemberRole.USER);

            if (i>=90){
                member.addRole(MemberRole.ADMIN);
            }
            memberRepostiry.save(member);
        });
    }

    @Test
    public void testRead() {
        Optional<Member> result = memberRepostiry.getWithRoles("member100");

        Member member = result.orElseThrow();

        log.info(member);
        log.info(member.getRoleSet());

        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
    }
}