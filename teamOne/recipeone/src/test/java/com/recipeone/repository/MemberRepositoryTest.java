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

    @Commit
    @Test
    public void testUpdate() {

        String mid = "wjstktpghd2@naver.com"; //소셜 로그인으로 추가된 사용자로 현재 DB에 존재하는 이메일
        String password =passwordEncoder.encode("54321");

        memberRepository.updatePassword(password,mid);
    }


}