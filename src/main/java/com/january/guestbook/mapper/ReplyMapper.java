package com.january.guestbook.mapper;

import com.january.guestbook.domain.Board;
import com.january.guestbook.domain.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    Reply getReplyById(Long rno);

    List<Reply> getRepliesByGnoOrderByBoard(Board board);

    void insert(Reply reply);

    void modify(Reply reply);

    // 게시물에 달린 댓글 전체 삭제
    void deleteByGno(Long gno);

    // 댓글 삭제
    void deleteByRno(Long rno);
}
