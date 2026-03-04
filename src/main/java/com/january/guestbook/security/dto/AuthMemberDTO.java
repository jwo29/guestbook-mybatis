package com.january.guestbook.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {

    private String email;
    private String name;
    private boolean fromSocial;

    // OAuth2User는 Map타입으로 모든 인증 결과를 attributes라는 이름의 변수로 가지고 있음.
    // 따라서, 해당 클래스 역시 attr이라는 변수를 만들어주고 getAttributes()를 override 하게 한다.
    private Map<String, Object> attr;

    public AuthMemberDTO(String username,
                         String password,
                         boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities,
                         Map<String, Object> attr) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial =fromSocial;
        this.attr = attr;
    }

    public AuthMemberDTO(String username,
                         String password,
                         boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial =fromSocial;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attr;
    }
}
