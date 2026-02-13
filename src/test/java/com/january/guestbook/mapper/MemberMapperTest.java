package com.january.guestbook.mapper;

import com.january.guestbook.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.LongStream;

@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void insert() {
        LongStream.rangeClosed(2, 100).forEach(i -> {
            Member member = Member.builder()
                    .mno(i)
                    .email("user" + i + "aaa.com")
                    .password("1111")
                    .name("USER" + i)
                    .build();

            memberMapper.save(member);
        });
    }
}
