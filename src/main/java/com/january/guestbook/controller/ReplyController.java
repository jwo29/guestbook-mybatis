package com.january.guestbook.controller;

import com.january.guestbook.domain.Reply;
import com.january.guestbook.dto.ReplyModifyDTO;
import com.january.guestbook.dto.ReplyRegisterDTO;
import com.january.guestbook.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping(value = "/board/{gno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reply>> getListByBoard(@PathVariable("gno") Long gno) {
        log.info("gno: " + gno);
        return ResponseEntity.ok(replyService.getList(gno));
    }

    @PostMapping("")
    public ResponseEntity<Long> createReply(@RequestBody ReplyRegisterDTO replyRegisterDTO) {
        log.info("replyRegisterDTO: " + replyRegisterDTO);

        Long rno = replyService.register(replyRegisterDTO);

        return ResponseEntity.ok(rno);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyModifyDTO replyModifyDTO) {
        log.info("replyModifyDTO: " + replyModifyDTO);

        replyService.modify(replyModifyDTO);

        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
        log.info("rno: " + rno);

        replyService.delete(rno);

        return ResponseEntity.ok("success");
    }
}
