package com.january.guestbook.domain;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long mno;
    private String email;
    private String password;
    private String name;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
