package com.january.guestbook.config;

import com.january.guestbook.security.filter.ApiLoginFilter;
import com.january.guestbook.security.filter.JWTAuthenticationFilter;
import com.january.guestbook.security.filter.JWTExceptionFilter;
import com.january.guestbook.security.handler.ApiLoginFailHandler;
import com.january.guestbook.security.handler.MemberLoginSuccessHandler;
import com.january.guestbook.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    /**
     * SecurityFilterChain #1 - API Security (Stateless)
     * - API 서버
     * - /api/**
     * - JWT / Token 인증
     * - STATELESS
     *
     * 흐름:
     * Client
     * ↓
     * POST /api/login
     * ↓
     * ApiLoginFilter
     * ↓
     * AuthenticationManager
     * ↓
     * UserDetailsService
     * ↓
     * Authentication 성공
     * ↓
     * Token 생성
     * ↓
     * JSON 응답
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http, AuthenticationConfiguration configuration) throws Exception {

        AuthenticationManager authenticationManager = configuration.getAuthenticationManager();

        ApiLoginFilter apiLoginFilter = new ApiLoginFilter(authenticationManager, jwtUtil);

        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());

        JWTExceptionFilter jwtExceptionFilter = new JWTExceptionFilter();

        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(jwtUtil, userDetailsService);

        http
                .securityMatcher("/api/**", "/v2/board/**")

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 로그인 방식이 세션 기반 + 브라우저 서비스라면 CSRF 토큰 비활성화는 보안 상 위험하다.
                // Stateless API(JWT, Bearer Token)과 같이 Authorization 헤더 기반 인증인 경우에는 CSRF 토큰이 불필요하니,
                // 이 경우에는 비활성화해도 상관없다.
                .csrf(AbstractHttpConfigurer::disable)

                // 반드시 필요
                .authenticationManager(authenticationManager)

                // JWTExceptionFilter 는 ApiCheckFilter 앞에 위치해야 ApiCheckFilter에서 발생한 예외를 잡을 수 있다.
                .addFilterBefore(jwtExceptionFilter, UsernamePasswordAuthenticationFilter.class)
                // 일반적으로 ApiCheckFilter를 ApiLoginFilter 앞에 배치하는 이류는 다음 때문이다.
                // JWT 인증 → 가장 먼저 수행
                // 로그인 처리 → 특수 케이스
                .addFilterAfter(jwtAuthenticationFilter, JWTExceptionFilter.class)
                .addFilterAfter(apiLoginFilter, JWTAuthenticationFilter.class)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/v2/board/**").authenticated()
                );
        return http.build();
    }

    /**
     * SecurityFilterChain #2 - Web Security (Session 기반)
     * - 웹 서비스
     * - /**
     * - Session 기반 로그인
     * - formLogin + OAuth2
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/sample/all").permitAll()
                        /** requestMatchers(String ...) 는 내부적으로 MvcRequestMatcher로 동작한다. 이 경우,
                         * - DispatcherServlet 경로 기준
                         * - ServletPath 영향
                         * - PathPatternParser 영향
                         * 등으로 인해 단순 문자열 매칭이 다르게 동작할 수 있다.
                         * ==> **그래서** 정적 리소스는 PathRequest로 허용하는 것이 공식 권장 방법이다.
                         **/
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        /** USER라는 단어의 의미는 "ROLE_USER"라는 상수와 같은 의미이다.
                         * 스프링 시큐리티 내부에서 USER라는 단어르 상수처럼 인증된 사용자를 의미하는 용도로 사용한다.
                         * **로그인에 성공하면** 사용자는 'ROLE_USER'라는 권한을 가지도록 지정된다.
                         **/
                        .requestMatchers("/sample/member").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true)
                )
                .oauth2Login(oauth -> oauth
//                        .defaultSuccessUrl("/", true) // successHandler가 설정되면 defaultSuccessUrl은 무시된다.
                        .successHandler(successHandler())
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )
                // Remember me 설정 시 자동으로 기본 로그인 화면에 자동 로그인 여부 체크박스가 생성된다.
                // ※ 소셜 로그인으로 로그인했을 때는 Remember me를 사용할 수 없다.(즉, remember-me 쿠키를 생성하지 않는다)
                .rememberMe(me -> me
                        .tokenValiditySeconds(60*60*24*7) // 7일
                        .userDetailsService(userDetailsService)
                )

//                .csrf(Customizer.withDefaults()) // 실무 권장
                .csrf(AbstractHttpConfigurer::disable) // 테스트용
        ;

        return http.build();
    }

    /**
     * PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * OAuth2 Login Success Handler
     */
    @Bean
    public MemberLoginSuccessHandler successHandler() {
        return new MemberLoginSuccessHandler(passwordEncoder());
    }

}
