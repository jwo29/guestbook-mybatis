package com.january.guestbook.mapper;

import com.january.guestbook.domain.Member;
import com.january.guestbook.domain.Movie;
import com.january.guestbook.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReviewMapperTest {

    @Autowired
    private ReviewMapper reviewMapper;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 200).forEach(i -> {

            // 영화 번호
            Long mno = (long)(Math.random()*100) + 1;

            // 유저(리뷰어) 번호
            Long mid = (long)(Math.random()*100) + 1;

            Review review = Review.builder()
                    .grade((int)(Math.random()*5) + 1)
                    .text("이 영화에 대한 감상..." + i)
                    .movie(Movie.builder().mno(mno).build())
                    .member(Member.builder().mno(mid).build())
                    .build();

            reviewMapper.insert(review);
        });
    }
}
