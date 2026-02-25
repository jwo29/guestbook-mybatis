package com.january.guestbook.service;

import com.january.guestbook.domain.Movie;
import com.january.guestbook.domain.MovieImage;
import com.january.guestbook.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MovieService {
    PageResultDTO<MovieListDTO> movieList(PageRequestDTO requestDTO);
    Long register(MovieRegisterDTO movieRegisterDTO);
    MovieDTO getMovieWithAll(Long mno);


    // Map 타입으로 변환
    default Map<String, Object> convertToMap(MovieRegisterDTO movieRegisterDTO) {

        Map<String, Object> map = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieRegisterDTO.getMno())
                .title(movieRegisterDTO.getTitle())
                .build();

        map.put("movie", movie);

        List<MovieImageDTO> imageDTOList = movieRegisterDTO.getImages();

        // MovieImageDTO 처리
        if (imageDTOList != null && !imageDTOList.isEmpty()) {
            List<MovieImage> movieImageList = imageDTOList.stream()
                    .map(movieImageDTO -> MovieImage.builder()
                            .path(movieImageDTO.getPath())
                            .imgName(movieImageDTO.getImgName())
                            .uuid(movieImageDTO.getUuid())
                            .movie(movie)
                            .build()).toList();
            map.put("imageList", movieImageList);
        }

        return map;
    }
}
