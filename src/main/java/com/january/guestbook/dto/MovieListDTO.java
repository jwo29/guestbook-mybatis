package com.january.guestbook.dto;

import lombok.Data;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Data
public class MovieListDTO {
    private Long mno;
    private String title;
    private int reviewCount;
    private double gradeAverage;
    private LocalDateTime regDate;

    // 썸네일용 필드 직접 추가
    private String thumbUuid;
    private String thumbImgName;
    private String thumbPath;

    public String getThumbnailURL() {
        if (thumbUuid == null) return null;
        return URLEncoder.encode(thumbPath + "/s_" + thumbUuid + "_" + thumbImgName, StandardCharsets.UTF_8);
    }
}
