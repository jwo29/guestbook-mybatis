package com.january.guestbook.security.filter;

import com.january.guestbook.security.dto.AuthMemberDTO;
import com.january.guestbook.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private JWTUtil jwtUtil;

    public ApiLoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super(new AntPathRequestMatcher("/api/login", "POST"));
        setAuthenticationManager(authenticationManager);

        this.jwtUtil = jwtUtil;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException {

        log.info(authResult.getPrincipal());

        // email address
        String email = ((AuthMemberDTO) authResult.getPrincipal()).getEmail();

        String token = null;

        try {
            token = jwtUtil.generateToken(email);

            response.setContentType("text/plain");
            response.getOutputStream().write(token.getBytes());

            log.info(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        UserDetails user = (UserDetails) authResult.getPrincipal();

//        // 여기서 JWT 생성
//        String token = jwtUtil.generateToken(user.getUsername());
//
//        response.setContentType("application/json");
//        response.getWriter().write("{\"token\":\""+token+"\"}");
    }

}
