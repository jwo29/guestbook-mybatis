package com.january.guestbook.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieImage {
    private Long inum;
    private String uuid;
    private String path;
    private String imgName;

    private Movie movie;
}
