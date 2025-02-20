package com.example.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//이 파일이 스프링의 환경 설정 파일임을 의미
@Configuration
//모든 요청 URL 이 스프링 시큐리티의 제어를 받도록 만드는 @ >> 스프링 시큐리티를 활성화하는 역할
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    //SecurityFilterChain 클래스가 동작하여 모든 요청 URL 에 이 클래스가 필터로 적용되어 URL 별로 특별한 설정을 할 수 있게 된다.
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //인증되지 않은 모든 페이지의 요청을 허락한다는 의미 >> 따라서 로그인 XX -> 모든 페이지에 접근 가능
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/mysql/**")))
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
