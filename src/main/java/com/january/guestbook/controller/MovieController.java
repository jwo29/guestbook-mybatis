package com.january.guestbook.controller;

import com.january.guestbook.dto.MovieListDTO;
import com.january.guestbook.dto.PageRequestDTO;
import com.january.guestbook.dto.PageResultDTO;
import com.january.guestbook.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping({"/", "/list"})
    public String list(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model) {
        log.info("List page request - page: {}, size: {}"
                , pageRequestDTO.getPage(), pageRequestDTO.getSize());

        // 페이징 처리된 방명록 목록 조회
        PageResultDTO<MovieListDTO> result = movieService.movieList(pageRequestDTO);

        model.addAttribute("result", result);

        log.info("Total Count: {}, Total pages: {}", result.getTotalCount(), result.getTotalPage());

        return "/movie/list";
    }
}
