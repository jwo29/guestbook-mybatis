package com.january.guestbook.mapper;

import com.january.guestbook.domain.Movie;
import com.january.guestbook.domain.Review;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
    void insert(Review review);
    Review findByMovie(Movie movie);
}
