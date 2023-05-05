package com.recipeone.security;

import com.recipeone.entity.Member;
import com.recipeone.repository.MemberRepository;
import com.recipeone.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor

public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {
        log.info("loadUserByUsername" + user_name);

        Optional<Member> result = memberRepository.getWithRoles(user_name);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("user_name not found...");
        }

        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO =
                new MemberSecurityDTO(
                        member.getUser_id(),
                        member.getUser_password(),
                        false,
                        member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name())).collect(Collectors.toList())
                );

        log.info("memberSecurityDTO");

        return memberSecurityDTO;
    }
}
