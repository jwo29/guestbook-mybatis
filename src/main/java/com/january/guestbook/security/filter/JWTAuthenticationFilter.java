package com.january.guestbook.security.filter;

import com.january.guestbook.security.dto.AuthMemberDTO;
import com.january.guestbook.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        log.info("Request URI: " + uri);

        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            log.info("Authorization exist: {}", authHeader);

            String token = authHeader.substring("Bearer ".length());

            String email = jwtUtil.validateAndExtract(token);
            log.info("validate result: {}", email);

            AuthMemberDTO authMemberDTO = (AuthMemberDTO) userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    authMemberDTO,
                    null,
                    authMemberDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // 특정 요청에서 해당 필터를 아예 실행하지 않도록 한다.
    // 실무에서는 Authorization 헤더를 검사하는 방식과 함께 사용하는 패턴이 가장 흔하다.
    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();

        log.info("shouldNotFilter check: {}", uri);

        return uri.startsWith("/api/login")
                || uri.startsWith("/oauth2/")
                || uri.startsWith("/css/")
                || uri.startsWith("/js/");
    }
}
