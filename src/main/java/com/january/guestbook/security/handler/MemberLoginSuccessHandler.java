package com.january.guestbook.security.handler;

import com.january.guestbook.security.dto.AuthMemberDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder;

    public MemberLoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("---------------------------------");
        log.info("onAuthenticationsSuccess");

        AuthMemberDTO authMemberDTO = (AuthMemberDTO) authentication.getPrincipal();

        boolean fromSocial = authMemberDTO.isFromSocial();

        log.info("Need Modify Member? " + fromSocial);

        // 사실 이 방식은 좋지 않다. 왜냐하면,
        // 1. 소셜 사용자는 실제로 비밀번호를 쓰지 않는다
        // 2. "1111"이라는 매직 값을 코드에서 비교하는 것은 취약하다
        // 3. 나중에 정책이 바뀌면 전체 코드 수정이 필요하다.(초기 비밀번호 세팅부터 그 값까지..)
        boolean passwordResult = passwordEncoder.matches("1111", authMemberDTO.getPassword());

        // 더 안전한 방식은, DB 컬럼을 하나 더 추가하여 '값'이 아닌 '상태'로써 로직을 검증하는 것이다.
        // EX) member.isFromSocial() && member.isProfileCompleted()

        // 소셜 로그인 && (초기)비밀번호가 1111
        if (fromSocial && passwordResult) {
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
        } else {
            // 반드시 기본 경로로 보내줘야 함
            redirectStrategy.sendRedirect(request, response, "/");
        }
    }
}
