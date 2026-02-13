package com.january.guestbook.dto;

import lombok.Data;

@Data
public class BoardModifyDTO {

    private Long gno;
    private String title;
    private String content;

}
