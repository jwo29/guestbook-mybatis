package com.january.guestbook.security;

import com.january.guestbook.security.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JWTUtilTest {

    /*
    JWTUtil 클래스는 스프링을 이용하는 테스트가 아니므로 내부에서 직접 JWTUtil 객체를 만들어서 사용할 필요가 있다.
     */
    private JWTUtil jwtUtil;

    @BeforeEach
    public void testBefore() {
        System.out.println("testBefore ==========================");
        jwtUtil = new JWTUtil();
    }

    @Test
    public void testEncode() throws Exception {
        String email = "user100@aaa.com";
        String str = jwtUtil.generateToken(email);
        System.out.println(str);
    }

    @Test
    public void testValidate() throws Exception {
        String email = "user100@aaa.com";

        String str = jwtUtil.generateToken(email);
        System.out.println(str);

        Thread.sleep(5000); // 5s. 토큰 유효기간은 임의로 1초로 지정한 후 테스트 해보면 validate에서 error 발생

        String resultEmail = jwtUtil.validateAndExtract(str);
        System.out.println(resultEmail);

    }
}
