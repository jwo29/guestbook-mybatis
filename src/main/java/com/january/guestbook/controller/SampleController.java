package com.january.guestbook.controller;

import com.january.guestbook.security.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    // 로그인을 하지 않아도 접근 가능
    @GetMapping("/all")
    public void all() {
        log.info("all() ............");
    }

    // 로그인한 사용자만 접근 가능
    @GetMapping("/member")
    public void member(@AuthenticationPrincipal AuthMemberDTO member) {
        log.info("member() ............");

        // @AuthenticationPrincipal 어노테이션을 사용하면 로그인된 사용자 정보를,
        // 별도의 캐스팅 없이 DTO로 사용할 수 있다.
        log.info("-------------------------------------");
        log.info(member);

    }

    // 관리자 권한이 있는 사용자만 접근 가능
    @GetMapping("/admin")
    public void admin() {
        log.info("admin() ............");
    }
}
