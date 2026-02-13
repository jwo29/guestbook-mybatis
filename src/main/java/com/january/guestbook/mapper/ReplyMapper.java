package com.january.guestbook.mapper;

import com.january.guestbook.domain.Board;
import com.january.guestbook.domain.Reply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyMapper {
    void insert(Reply reply);
}
