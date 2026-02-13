package com.january.guestbook.mapper;

import com.january.guestbook.domain.Board;
import com.january.guestbook.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootTest
public class BoardMapperTest {

    @Autowired
    private BoardMapper boardMapper;

    @Test
    public void findById() {
        long gno = (long) (Math.random() * 100) + 1;

        Board board = boardMapper.findByGno(847L);

        System.out.println(board);
    }

    @Test
    public void insert() {
        LongStream.range(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .mno(i)
                    .email("user" + i + "@aaa.com")
                    .build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();

            boardMapper.insert(board);
        });
    }
}
