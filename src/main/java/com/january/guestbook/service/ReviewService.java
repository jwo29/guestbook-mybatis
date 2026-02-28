package com.january.guestbook.service;

import com.january.guestbook.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    // 영화의 모든 영화리뷰를 가져온다.
    List<ReviewDTO> getListOfMovie(Long mno);

    // 영화 리뷰 추가
    Long register(ReviewDTO reviewDTO);

    // 특정 영화리뷰 수정
    void modify(ReviewDTO reviewDTO);

    // 영화 리뷰 삭제
    void delete(Long reviewNum);
}
