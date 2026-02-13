package com.january.guestbook.service;

import com.january.guestbook.domain.Board;
import com.january.guestbook.dto.*;

public interface BoardService {

    PageResultDTO<BoardListDTO> boardList(PageRequestDTO requestDTO);
    Long register(BoardRegisterDTO boardRegisterDTO);
    Board read(Long gno);
    void modify(BoardModifyDTO boardModifyDTO);
    void delete(Long gno);
}
