package com.january.guestbook.controller;

import com.january.guestbook.dto.*;
import com.january.guestbook.service.BoardService;
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
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

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
        PageResultDTO<BoardListDTO> result = boardService.boardList(pageRequestDTO);

        // 모델에 결과 담기
        model.addAttribute("result", result);

        log.info("Total count: {}, Total pages: {}", result.getTotalCount(), result.getTotalPage());

        return "/board/list";
    }

    //---------------------------------------------------------------
    /**
     * Spring MVC에서 컨트롤러 메서드가 void를 반환하거나 반환 타입이 없으면,
     * Spring은 요청 URL 경로를 그대로 뷰 이름으로 사용한다.
     * 즉 RequestToViewNameTranslator가 자동으로 작동한다:
     * 1. 반환 타입이 void이거나 null일 때
     * 2. 요청 경로에서 자동으롭 뷰 이름을 생성
     * 3. /board/register → board/register(앞의 / 제거)
     */
    @GetMapping("/register")
    public void register() {
        log.info("Register get...");
    }

    @PostMapping("/register")
    public String registerPost(BoardRegisterDTO boardRegisterDTO, RedirectAttributes redirectAttributes) {
        log.info("Register post: {}", boardRegisterDTO);

        // 새로 추가된 엔티티의 번호
        Long gno = boardService.register(boardRegisterDTO);

        // addFlashAttribute: 단 한 번만 데이터를 전달하는 용도. 왜? addAttribute와는 무슨 차이?
        // ㄴ 데이터 전달 방식과 URL 노출 여부에 차이가 있음
        // addFlashAttribute 특징
        // * 데이터가 세션에 임시 저장됨
        // * 리다이렌트 후 URL: /board/list (깔끔!)
        // * 브라우저 주소창에 안 보임
        // * 단 한 번만 사용 가능 (한 번 조회되면 세션에서 자동 삭제)
        // * 페이지 새로고침하면 사라짐
        // => "123번 글이 등록되었습니다"같은 일회성 메시지를 전달하기 위해! -> URL에 불필요한 정보를 노출하지 않기 위해!
        redirectAttributes.addFlashAttribute("msg", gno + "번째 방명록이 등록되었습니다");

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void readPost(long gno,
                         @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO, // @ModelAttribute 선언은 없어도 처리가 가능하지만 명시적으로 "requestDto"라는 이름으로 처리
                         Model model) {
        log.info("Read post: {}", gno);
        BoardDTO boardDTO = boardService.read(gno);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modifyPost(BoardModifyDTO boardModifyDTO,
                             @ModelAttribute("requestDto") PageRequestDTO requestDTO,
                             RedirectAttributes redirectAttributes) {
        log.info("Modify post: {}", boardModifyDTO);

        boardService.modify(boardModifyDTO);

        // 리다이렉션 시 전달할 속성(파라미터 일치 시키면 됨)
        // addAttribute 특징(위의 addFlashAttribute와 비교해보기)
        // * 데이터가 URL 쿼리 파라미터로 전달됨
        // * 리다이렉트 후 URL: /board/read?gno=123&page=1
        // * 브라우저 주소창에 보임
        // * 북마크 가능, 페이지 새로고침해도 유지됨
        // * GET 요청의 파라미터로 전달되어야 할 데이터에 사용
        // => /read 페이지는 gno, page 파라미터가 필요하니까! -> 사용자가 URL을 북마크하거나 공유할 수 있어야 하니까!
        redirectAttributes.addAttribute("gno", boardModifyDTO.getGno()); // addFlashAttribute 사용하면 /read 메서드의 long gno 파라미터가 값을 받지 못 한다(URL에 없으니까)
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String removePost(long gno, RedirectAttributes redirectAttributes) {
        log.info("Remove post: {}", gno);
        boardService.deleteWithReplys(gno);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/board/list";
    }
}
