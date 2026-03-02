package com.january.guestbook.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/sample/all").permitAll()
                        // requestMatchers(String ...) 는 내부적으로 MvcRequestMatcher로 동작한다. 이 경우,
                        // - DispatcherServlet 경로 기준
                        // - ServletPath 영향
                        // - PathPatternParser 영향
                        // 등으로 인해 단순 문자열 매칭이 다르게 동작할 수 있다.
                        // .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        // ==> **그래서** 정적 리소스는 PathRequest로 허용하는 것이 공식 권장 방법이다.
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // USER라는 단어의 의미는 "ROLE_USER"라는 상수와 같은 의미이다.
                        // 스프링 시큐리티 내부에서 USER라는 단어르 상수처럼 인증된 사용자를 의미하는 용도로 사용한다.
                        // **로그인에 성공하면** 사용자는 'ROLE_USER'라는 권한을 가지도록 지정된다.
                        .requestMatchers("/sample/member").hasRole("USER")
                        .anyRequest().authenticated()
                )
                // 인증/인가 문제 시 로그인 화면으로 이동
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )
                // 로그인 방식이 세션 기반 + 브라우저 서비스라면 CSRF 토큰 비활성화는 보안 상 위험하다.
                // **학습 목적이기에 이렇게 세팅하는 것이고**,
                // Stateless API(JWT, Bearer Token)과 같이 Authorization 헤더 기반 인증인 경우에는 CSRF 토큰이 불필요하니,
                // 이 경우에는 비활성화해도 상관없다.
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
////        return new InMemoryUserDetailsManager(
////                User.builder()
////                        .username("user1")
////                        .password(passwordEncoder.encode("1111"))
////                        .roles("USER")
////                        .build()
////        );
//        return new MemberUserDetailService();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
