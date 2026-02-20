package com.january.guestbook.service;

import com.january.guestbook.dto.MovieListDTO;
import com.january.guestbook.dto.PageRequestDTO;
import com.january.guestbook.dto.PageResultDTO;
import com.january.guestbook.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieMapper movieMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResultDTO<MovieListDTO> movieList(PageRequestDTO requestDTO) {
        log.info("Get movie list - page: {}, size: {}", requestDTO.getPage(), requestDTO.getSize());

        // 전체 게시글 수 조회
        long totalCount = movieMapper.countAll(requestDTO);
        log.info("Total count: {}", totalCount);

        // 페이징 처리된 목록 조회
        List<MovieListDTO> movieList = movieMapper.findAll(requestDTO);
        log.info("Retrieved {} movies",  movieList.size());

        // PageResultDTO 생성 및 반환
        return new PageResultDTO<>(
                movieList,
                totalCount,
                requestDTO.getPage(),
                requestDTO.getSize()
        );
    }
}
