package com.recipeone.config;

import com.recipeone.repository.MemberRepository;
import com.recipeone.security.CustomUserDetailService;
import com.recipeone.security.handler.Custom403Handler;
//import com.recipeone.security.handler.CustomAuthenticationFailureHandler;
import com.recipeone.security.handler.CustomLoginFailureHandler;
import com.recipeone.security.handler.CustomSocialLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.persistence.Access;
import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        private final DataSource dataSource;
        private final CustomUserDetailService userDetailService;
    private final MemberRepository memberRepository;




    @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            log.info("-------------conofigure--------------");

            http.formLogin().loginPage("/member/login")    .failureHandler(authenticationFailureHandler());
            ;



            http.csrf().disable();

            http.rememberMe()
                    .key("12345678") //application properties에서 나중에 바꿔줄것
                    .tokenRepository(persistentTokenRepository())
                    .userDetailsService(userDetailService)
                    .tokenValiditySeconds(60*60*24*30);

            http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

            http.oauth2Login().loginPage("/member/login").successHandler(authenticationSuccessHandler());

            return http.build();
        }
        @Bean //정적 메서드 시큐리티 제외
        public WebSecurityCustomizer webSecurityCustomizer(){
            log.info("-------------web conofigure--------------");

            return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() { //rememberme db에 저장
            JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
            repo.setDataSource(dataSource);
            return repo;
        }

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean //권한 제한 될 때
        public AccessDeniedHandler accessDeniedHandler(){
            return new Custom403Handler();
        }

        @Bean //소셜 로그인 성공
        public AuthenticationSuccessHandler authenticationSuccessHandler(){
            return new CustomSocialLoginSuccessHandler(passwordEncoder());

        }
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomLoginFailureHandler(memberRepository);
    }


}
