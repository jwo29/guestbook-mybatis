package com.january.guestbook.service;

import com.january.guestbook.domain.Guestbook;
import com.january.guestbook.dto.PageRequestDTO;
import com.january.guestbook.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTest {

    @Autowired
    private GuestbookService guestbookService;

    @Test
    public void testRegister() {
        Guestbook guestbook = Guestbook.builder()
                .title("Sample Title")
                .content("Sample Content")
                .writer("user0")
                .build();

        guestbookService.register(guestbook);
    }

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO().builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("스프링")
                .build();

        PageResultDTO<Guestbook> resultDTO = guestbookService.guestbookList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("=====================================");
        for (Guestbook guestbook : resultDTO.getData()) {
            System.out.println(guestbook);
        }

        System.out.println("=====================================");
        resultDTO.getData().forEach(System.out::println);

    }
}
