package com.january.guestbook.service;

import com.january.guestbook.domain.Board;
import com.january.guestbook.domain.Reply;
import com.january.guestbook.dto.ReplyModifyDTO;
import com.january.guestbook.dto.ReplyRegisterDTO;
import com.january.guestbook.mapper.BoardMapper;
import com.january.guestbook.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper;
    private final BoardMapper boardMapper;

    @Override
    public Long register(ReplyRegisterDTO replyRegisterDTO) {

        Board board = boardMapper.findByGno(replyRegisterDTO.getGno());

        if (board != null) {
            Reply reply = Reply.builder()
                    .text(replyRegisterDTO.getText())
                    .replyer(replyRegisterDTO.getReplyer())
                    .board(board)
                    .build();

            replyMapper.insert(reply);

            return reply.getRno();
        } else {
            return null;
        }
    }

    @Override
    public List<Reply> getList(Long gno) {

        Board board = boardMapper.findByGno(gno);

        return replyMapper.getRepliesByGnoOrderByBoard(board);
    }

    @Override
    public void modify(ReplyModifyDTO replyModifyDTO) {
        Reply reply = replyMapper.getReplyById(replyModifyDTO.getRno());

        if (reply != null) {
            reply.changeText(replyModifyDTO.getText());

            replyMapper.modify(reply);
        }
    }

    @Override
    public void delete(Long rno) {
        replyMapper.deleteByRno(rno);
    }
}
