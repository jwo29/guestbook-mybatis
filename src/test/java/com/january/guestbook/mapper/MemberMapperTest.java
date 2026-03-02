package com.january.guestbook.mapper;

import com.january.guestbook.domain.Member;
import com.january.guestbook.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.LongStream;

@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insert() {
        LongStream.rangeClosed(2, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .password("1111")
                    .name("USER" + i)
                    .build();

            memberMapper.save(member);
        });
    }

    @Test
    public void updateMemberRole() {
        LongStream.rangeClosed(1, 100).forEach(i -> {
            Member member = memberMapper.getMemberByEmail("user" + i + "@aaa.com");

            if (i < 70) {
                member.addMemberRole(MemberRole.USER);
            } else if (i < 90) {
                member.addMemberRole(MemberRole.MANAGER);
            } else {
                member.addMemberRole(MemberRole.ADMIN);
            }

            memberMapper.setMemberRole(member);
        });
    }
}
