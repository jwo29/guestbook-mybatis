package com.january.guestbook.mapper;

import com.january.guestbook.domain.Board;
import com.january.guestbook.dto.BoardListDTO;
import com.january.guestbook.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    Board findByGno(Long gno);

    List<BoardListDTO> findAll(PageRequestDTO requestDTO);

    long countAll(PageRequestDTO requestDTO);

    void insert(Board board);

    void update(Board board);

    void deleteByGno(Long gno);

}
