package com.january.guestbook.mapper;

import com.january.guestbook.domain.Movie;
import com.january.guestbook.dto.MovieListDTO;
import com.january.guestbook.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {
    void insert(Movie movie);

    List<MovieListDTO> findAll(PageRequestDTO requestDTO);

    long countAll(PageRequestDTO requestDTO);
}
