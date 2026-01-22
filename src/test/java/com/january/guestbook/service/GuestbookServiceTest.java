package com.january.guestbook.service;

import com.january.guestbook.domain.Guestbook;
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
}
