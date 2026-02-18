package com.january.guestbook.service;

import com.january.guestbook.domain.Reply;
import com.january.guestbook.dto.ReplyModifyDTO;
import com.january.guestbook.dto.ReplyRegisterDTO;

import java.util.List;

public interface ReplyService {

    Long register(ReplyRegisterDTO replyRegisterDTO);

    List<Reply> getList(Long gno);

    void modify(ReplyModifyDTO replyModifyDTO);

    void delete(Long rno);

}
