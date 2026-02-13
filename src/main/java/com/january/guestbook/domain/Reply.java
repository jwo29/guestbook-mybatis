package com.january.guestbook.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class Reply {

    private Long rno;
    private String text;
    private String replyer;

    private Board board;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
