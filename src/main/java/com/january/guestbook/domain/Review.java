package com.january.guestbook.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long reviewNum;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private int grade;
    private String text;

    private Movie movie;
    private Member member;

    public void changeGrade(int grade) {
        this.grade = grade;
    }
    public void changeText(String text) {
        this.text = text;
    }
}
