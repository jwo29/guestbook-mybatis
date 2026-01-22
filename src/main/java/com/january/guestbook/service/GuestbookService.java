package com.january.guestbook.service;

import com.january.guestbook.domain.Guestbook;
import com.january.guestbook.dto.PageRequestDTO;
import com.january.guestbook.dto.PageResultDTO;

import java.awt.print.Pageable;

public interface GuestbookService {

    Long register(Guestbook guestbook);

    PageResultDTO<Guestbook> guestbookList(PageRequestDTO requestDTO);
}
