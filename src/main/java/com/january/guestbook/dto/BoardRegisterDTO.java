package com.january.guestbook.dto;

import lombok.Data;

@Data
public class BoardRegisterDTO {
    private String title;
    private String content;
    private String writerEmail;
}
