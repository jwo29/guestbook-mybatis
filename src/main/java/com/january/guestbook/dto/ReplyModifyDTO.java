package com.january.guestbook.dto;

import lombok.Data;

@Data
public class ReplyModifyDTO {
    private Long gno;
    private Long rno;
    private String text;
}
