package com.january.guestbook.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDTO {
    private Long reviewNum;

    // Movie ID
    private Long mno;

    // Member ID
    private Long memberNo;
    private String name;
    private String email;

    private int grade;
    private String text;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
