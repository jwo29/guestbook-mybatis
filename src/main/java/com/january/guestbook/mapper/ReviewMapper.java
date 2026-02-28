package com.january.guestbook.mapper;

import com.january.guestbook.domain.Movie;
import com.january.guestbook.domain.Review;
import com.january.guestbook.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void insert(Review review);
    Review findByMovie(Movie movie);
    Review getByReviewNum(Long reviewNum);
    List<ReviewDTO> getListOfMovie(Long mno);
    void modify(Review review);
    void delete(Long reviewNum);
}
