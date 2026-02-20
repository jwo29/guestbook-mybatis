package com.january.guestbook.mapper;

import com.january.guestbook.domain.MovieImage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieImageMapper {
    void insert(MovieImage movieImage);
}
