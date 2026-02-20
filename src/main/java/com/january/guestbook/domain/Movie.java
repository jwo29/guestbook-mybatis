package com.january.guestbook.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Movie {
    private Long mno;
    private String title;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
