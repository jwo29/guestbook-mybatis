package com.january.guestbook.service;

import com.january.guestbook.dto.*;

import java.util.List;

public interface BoardService {

    PageResultDTO<BoardListDTO> boardList(PageRequestDTO requestDTO);
    Long register(BoardRegisterDTO boardRegisterDTO);
    BoardDTO read(Long gno);
    List<BoardDTO> readAll(String email);
    void modify(BoardModifyDTO boardModifyDTO);
    void delete(Long gno);
    void deleteWithReplys(Long gno);
}
