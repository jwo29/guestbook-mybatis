package com.january.guestbook.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieListDTO {
    private Long mno;
    private String title;
    private Long imageNumber;
    private int reviewCount;
    private long gradeAverage;

    private LocalDateTime regDate;
}
