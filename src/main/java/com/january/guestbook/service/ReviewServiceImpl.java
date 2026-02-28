package com.january.guestbook.service;

import com.january.guestbook.domain.Member;
import com.january.guestbook.domain.Movie;
import com.january.guestbook.domain.Review;
import com.january.guestbook.dto.ReviewDTO;
import com.january.guestbook.mapper.MemberMapper;
import com.january.guestbook.mapper.MovieMapper;
import com.january.guestbook.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final MovieMapper movieMapper;
    private final MemberMapper memberMapper;

    @Override
    public List<ReviewDTO> getListOfMovie(Long mno) {
        return reviewMapper.getListOfMovie(mno);
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {

        Movie movie = movieMapper.getById(reviewDTO.getMno());
        Member member = memberMapper.getMemberByEmail(reviewDTO.getEmail());

        Review review = Review.builder()
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .movie(movie)
                .member(member)
                .build();

        reviewMapper.insert(review);

        return review.getReviewNum();
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {

        Review review = reviewMapper.getByReviewNum(reviewDTO.getReviewNum());
        review.changeGrade(reviewDTO.getGrade());
        review.changeText(reviewDTO.getText());

        reviewMapper.modify(review);
    }

    @Override
    public void delete(Long reviewNum) {
        reviewMapper.delete(reviewNum);
    }
}
