package com.january.guestbook.security.filter;

import com.january.guestbook.security.dto.AuthMemberDTO;
import com.january.guestbook.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    public ApiCheckFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        log.info("Request URI: " + uri);

        log.info("ApiCheckFilter 실행 -------------------------------");

        // 여기서 JWT 검사
        if (!checkAuthHeader(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            // JSON 리턴
            String message = "FAIL CHECK API TOKEN";

            JSONObject json = new JSONObject();
            json.put("message", message);
            json.put("code", "403");

            PrintWriter out = response.getWriter();
            out.println(json);

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

                AuthMemberDTO authMemberDTO = (AuthMemberDTO) userDetailsService.loadUserByUsername(email);

                if (!email.isEmpty()) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            authMemberDTO.getAuthorities());

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
