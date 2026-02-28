package com.january.guestbook.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long mno;
    private String title;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
