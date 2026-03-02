package com.january.guestbook.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode(){
        String password = "1111";

        String encodedPassword = passwordEncoder.encode(password);

        // 암호화 결과는 매번 다르지만
        System.out.println(encodedPassword);

        boolean matchResult = passwordEncoder.matches(password, encodedPassword);

        // matches()의 결과는 매번 true이다.
        System.out.println(matchResult);
    }
}
