package com.january.guestbook.service;

import com.january.guestbook.domain.Board;
import com.january.guestbook.dto.BoardListDTO;
import com.january.guestbook.dto.BoardModifyDTO;
import com.january.guestbook.dto.PageRequestDTO;
import com.january.guestbook.dto.PageResultDTO;

public interface BoardService {

    PageResultDTO<BoardListDTO> boardList(PageRequestDTO requestDTO);
    Long register(Board board);
    Board read(Long gno);
    void modify(BoardModifyDTO boardModifyDTO);
    void delete(Long gno);
}
