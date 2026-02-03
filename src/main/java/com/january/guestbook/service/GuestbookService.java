package com.january.guestbook.service;

import com.january.guestbook.domain.Guestbook;
import com.january.guestbook.dto.PageRequestDTO;
import com.january.guestbook.dto.PageResultDTO;

public interface GuestbookService {

    PageResultDTO<Guestbook> guestbookList(PageRequestDTO requestDTO);
    Long register(Guestbook guestbook);
    Guestbook read(Long gno);
    void modify(Guestbook guestbook);
    void delete(Long gno);
}
