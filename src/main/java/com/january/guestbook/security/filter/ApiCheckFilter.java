package com.january.guestbook.security.filter;

import com.january.guestbook.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final String pattern;
    private final JWTUtil jwtUtil;

    public ApiCheckFilter(String pattern, JWTUtil jwtUtil) {
        this.pattern = pattern;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        log.info("Request URI: " + uri);

        // /v2/board/** 가 아니면 그냥 통과
        if (!antPathMatcher.match(pattern, uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("ApiCheckFilter 실행 -------------------------------");

        // 여기서 JWT 검사
        if (!checkAuthHeader(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkAuthHeader(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            log.info("Authorization exist: {}", authHeader);

            String token = authHeader.substring("Bearer ".length());

            try {
                String email = jwtUtil.validateAndExtract(token);
                log.info("validate result: {}", email);

                if (!email.isEmpty()) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER")));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return true;
                }

            } catch (Exception e) {
                log.error("JWT validation failed: {}", e.getMessage());
            }
        }

        return false;
    }
}
