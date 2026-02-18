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
public class Reply {

    private Long rno;
    private String text;
    private String replyer;

    private Board board;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public void changeText(String text) {
        this.text = text;
    }

    /**
     * @Builder 는 내부적으로 아래 코드를 생성한다.
     *
     * public static ReplyBuilder builder() {
     *     return new ReplyBuilder();
     * }
     *
     * public static class ReplyBuilder {
     *     public Reply build() {
     *         return new Reply(rno, text, replyer, board, regDate, modDate);
     *     }
     * }
     *
     * 즉, build()에서 모든 필드를 받는 생성자를 호출한다.
     * 만약, @NoArgsConstructor 만 선언하고,
     * @AllArgsConstructor 는 선언하지 않는다면, Builder가 호출할 생성자가 없기 때문에, Lombok 컴파일 오류가 발생한다.
     */
}
