package com.january.guestbook.dto;

import lombok.*;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter // 중요: Spring이 쿼리 파라미터를 바인딩하려면 Setter 필요!
@Builder
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type;
    private String keyword;

    // MyBatis에서 사용할 OFFSET 계산
    public int getOffset() {
        return (page - 1) * size;
    }

    /**
     * 페이지 링크 생성을 위한 헬퍼 메서드
     * @param page 이동할 페이지 번호
     * @return 쿼리 파라미터가 포함된 URL 문자열
     */
    public String getLink(int page) {
        return UriComponentsBuilder.fromPath("")
                .queryParam("page", page)
                .queryParam("size", this.size)
                .build()
                .toString();
    }

    /**
     * 현재 페이지 링크 반환
     * @return 현재 페이지의 쿼리 파라미터
     */
    public String getLink() {
        return getLink(this.page);
    }
}
