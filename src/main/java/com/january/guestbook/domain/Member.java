package com.january.guestbook.domain;


import com.january.guestbook.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private String email;       // 아이디 역할
    private String password;
    private String name;        // 닉네임

    private boolean fromSocial; // 소셜 로그인으로 회원 가입된 경우

    private Set<MemberRole> roleSet;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public void addMemberRole(MemberRole role) {
        roleSet.add(role);
    }

}
