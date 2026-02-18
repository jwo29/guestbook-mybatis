package com.january.guestbook.dto;

import lombok.Data;

@Data
public class ReplyRegisterDTO {
    private Long gno;

    private Long rno;
    private String text;
    private String replyer;
}
