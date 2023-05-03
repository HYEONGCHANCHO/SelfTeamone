package com.recipeone.security;

import com.recipeone.entity.Member;
import com.recipeone.repository.MemberRepostiry;
import com.recipeone.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor

public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepostiry memberRepostiry;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername" + username);

        Optional<Member> result = memberRepostiry.getWithRoles(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("user_name not found...");
        }

        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO =
                new MemberSecurityDTO(
                        member.getUser_id(),
                        member.getUser_password(),
                        member.getUser_email(),
                        false,
                        member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name())).collect(Collectors.toList())
                );

        log.info("memberSecurityDTO");
        log.info("memberSecurityDTO");

        return memberSecurityDTO;
    }
}
