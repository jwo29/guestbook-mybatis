package com.january.guestbook.mapper;

import com.january.guestbook.domain.Reply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyMapper {
    void insert(Reply reply);

    // 게시물에 달린 댓글 전체 삭제
    void deleteByGno(Long gno);

    // 댓글 삭제
    void deleteByRno(Long rno);
}
