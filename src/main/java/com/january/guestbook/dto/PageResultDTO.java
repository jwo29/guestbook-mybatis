package com.january.guestbook.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResultDTO<T> {
    private List<T> data;        // 조회된 데이터 목록
    private long totalCount;     // 전체 데이터 개수
    private int page;            // 현재 페이지 번호
    private int size;            // 페이지당 데이터 개수
    
    // 페이징 UI를 위한 추가 정보
    private int totalPage;       // 전체 페이지 개수
    private int start;           // 시작 페이지 번호
    private int end;             // 끝 페이지 번호
    private boolean prev;        // 이전 페이지 버튼 활성화 여부
    private boolean next;        // 다음 페이지 버튼 활성화 여부
    
    // 페이지 목록 번호 개수 (예: 10개)
    private static final int PAGE_DISPLAY_COUNT = 10;
    
    public PageResultDTO(List<T> data, long totalCount, int page, int size) {
        this.data = data;
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
        
        // 전체 페이지 개수 계산
        this.totalPage = (int) Math.ceil((double) totalCount / size);
        
        // 페이지 네비게이션 계산
        calculatePageNavigation();
    }
    
    private void calculatePageNavigation() {
        // 현재 페이지 기준으로 끝 페이지 계산
        this.end = (int) (Math.ceil((double) page / PAGE_DISPLAY_COUNT)) * PAGE_DISPLAY_COUNT;
        
        // 시작 페이지 계산
        this.start = end - PAGE_DISPLAY_COUNT + 1;
        
        // 실제 끝 페이지가 계산된 끝 페이지보다 작으면 조정
        if (end > totalPage) {
            this.end = totalPage;
        }
        
        // 이전/다음 버튼 활성화 여부
        this.prev = start > 1;
        this.next = end < totalPage;
    }
}
