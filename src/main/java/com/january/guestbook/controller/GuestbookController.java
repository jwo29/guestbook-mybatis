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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //---------------------------------------------------------------
    /**
     * Spring MVC에서 컨트롤러 메서드가 void를 반환하거나 반환 타입이 없으면,
     * Spring은 요청 URL 경로를 그대로 뷰 이름으로 사용한다.
     * 즉 RequestToViewNameTranslator가 자동으로 작동한다:
     * 1. 반환 타입이 void이거나 null일 때
     * 2. 요청 경로에서 자동으롭 뷰 이름을 생성
     * 3. /guestbook/register → guestbook/register(앞의 / 제거)
     */
    @GetMapping("/register")
    public void register() {
        log.info("Register get...");
    }

    @PostMapping("/register")
    public String registerPost(Guestbook guestbook, RedirectAttributes redirectAttributes) {
        log.info("Register post: {}", guestbook);

        // 새로 추가된 엔티티의 번호
        Long gno = guestbookService.register(guestbook);

        // addFlashAttribute: 단 한 번만 데이터를 전달하는 용도. 왜..???/
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }
}
