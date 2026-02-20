package com.january.guestbook.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Review {
    private Long reviewNum;
    private int grade;
    private String text;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private Movie movie;
    private Member member;
}
