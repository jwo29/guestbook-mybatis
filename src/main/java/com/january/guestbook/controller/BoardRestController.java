package com.january.guestbook.controller;

import com.january.guestbook.dto.BoardDTO;
import com.january.guestbook.dto.BoardModifyDTO;
import com.january.guestbook.dto.BoardRegisterDTO;
import com.january.guestbook.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// API 서버로서 역할하기 위한 컨트롤러(즉 화면이 없다)
@RestController
@Log4j2
@RequestMapping("/v2/board")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

    @PostMapping(value = "")
    public ResponseEntity<Long> register(@RequestBody BoardRegisterDTO boardRegisterDTO) {
        log.info("-------------- board register --------------");
        log.info("boardRegisterDTO: {}", boardRegisterDTO);

        Long gno = boardService.register(boardRegisterDTO);

        return ResponseEntity.ok(gno);
    }

    @GetMapping(value = "/{gno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoardDTO> read(@PathVariable("gno") Long gno) {
        log.info("-------------- board read --------------");
        log.info("gno: {}", gno);

        BoardDTO boardDTO = boardService.read(gno);
        return ResponseEntity.ok(boardDTO);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BoardDTO>> readAll(String email) {
        log.info("-------------- board readAll --------------");
        log.info("email: {}", email);

        List<BoardDTO> boardDTOList = boardService.readAll(email);
        return ResponseEntity.ok(boardDTOList);
    }

    @DeleteMapping(value = "/{gno}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> delete(@PathVariable("gno") Long gno) {
        log.info("-------------- board delete --------------");
        log.info("gno: {}", gno);

        boardService.delete(gno);

        return ResponseEntity.ok("removed");
    }

    @PutMapping(value = "/{gno}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody BoardModifyDTO boardModifyDTO) {
        log.info("-------------- board modify --------------");
        log.info("boardModifyDTO: {}", boardModifyDTO);

        boardService.modify(boardModifyDTO);

        return ResponseEntity.ok("modified");
    }

}
