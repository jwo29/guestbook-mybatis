package com.january.guestbook.service;

import com.january.guestbook.dto.MovieListDTO;
import com.january.guestbook.dto.PageRequestDTO;
import com.january.guestbook.dto.PageResultDTO;

public interface MovieService {
    PageResultDTO<MovieListDTO> movieList(PageRequestDTO requestDTO);
}
