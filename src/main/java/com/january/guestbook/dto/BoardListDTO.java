package com.january.guestbook.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListDTO {
    private Long gno;
    private String title;
    private int replyCount;
    private String writerName;
    private String writerEmail;

    private LocalDateTime regDate;
}
