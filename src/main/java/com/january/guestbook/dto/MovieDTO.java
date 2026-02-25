package com.january.guestbook.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieDTO {
    private Long mno;
    private String title;
    private int reviewCount;
    private double gradeAverage;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private List<MovieImageDTO> images;
}
