package com.january.guestbook.controller;

import com.january.guestbook.domain.Guestbook;
import com.january.guestbook.dto.PageRequestDTO;
import com.january.guestbook.dto.PageResultDTO;
import com.january.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    /**
     * 방명록 목록 조회 (페이징 처리)
     * @param pageRequestDTO 페이지 요청 정보 (page, size) - 파라미터로 전달되지 않으면 기본값 사용
     * @param model 뷰에 전달할 데이터
     * @return 목록 페이지 뷰 이름
     */
    @GetMapping({"/", "/list"})
    public String list(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model) {
        log.info("List page request - page: {}, size: {}",
                pageRequestDTO.getPage(), pageRequestDTO.getSize());

        // 페이징 처리된 방명록 목록 조회
        PageResultDTO<Guestbook> result = guestbookService.guestbookList(pageRequestDTO);

        // 모델에 결과 담기
        model.addAttribute("result", result);

        log.info("Total count: {}, Total pages: {}", result.getTotalCount(), result.getTotalPage());

        return "/guestbook/list";
    }
}
