package com.january.guestbook.service;

import com.january.guestbook.dto.BoardListDTO;
import com.january.guestbook.dto.BoardRegisterDTO;
import com.january.guestbook.dto.PageRequestDTO;
import com.january.guestbook.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        BoardRegisterDTO boardRegisterDTO = new BoardRegisterDTO();

        boardRegisterDTO.setTitle("Sample Title");
        boardRegisterDTO.setContent("Sample Content");
        boardRegisterDTO.setWriterEmail("user100@aaa.com");

        boardService.register(boardRegisterDTO);
    }

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO().builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("스프링")
                .build();

        PageResultDTO<BoardListDTO> resultDTO = boardService.boardList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("=====================================");
        for (BoardListDTO board : resultDTO.getData()) {
            System.out.println(board);
        }

        System.out.println("=====================================");
        resultDTO.getData().forEach(System.out::println);

    }
}
