package com.january.guestbook.mapper;

import com.january.guestbook.domain.Board;
import com.january.guestbook.domain.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.LongStream;

@SpringBootTest
public class ReplyMapperTest {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Test
    public void insert() {
        LongStream.rangeClosed(1, 300).forEach(i -> {

            long gno = (long) (Math.random() * 100) + 1;

            Board board = boardMapper.findByGno(gno);

            Reply reply = Reply.builder()
                    .rno(i)
                    .board(board)
                    .text("Reply...." + i)
                    .replyer(board.getWriter().getName())
                    .build();

            replyMapper.insert(reply);
        });
    }
}
