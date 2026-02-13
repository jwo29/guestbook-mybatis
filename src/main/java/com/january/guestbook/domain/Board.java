package com.january.guestbook.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private Long gno;
    private String title;
    private String content;
    private Member writer;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeContent(String content) {
        this.content = content;
    }
}
