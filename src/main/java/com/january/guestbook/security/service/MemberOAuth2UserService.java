package com.january.guestbook.security.service;

import com.january.guestbook.domain.Member;
import com.january.guestbook.entity.MemberRole;
import com.january.guestbook.mapper.MemberAuthMapper;
import com.january.guestbook.mapper.MemberMapper;
import com.january.guestbook.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberMapper memberMapper;
    private final MemberAuthMapper memberAuthMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        log.info("--------------------------------------------");
        log.info("userRequest: {}", request);

        String clientName = request.getClientRegistration().getClientName();

        log.info("clientName: {}", clientName);
        log.info(request.getAdditionalParameters());

        OAuth2User user = super.loadUser(request);

        log.info("==========================");
        user.getAttributes().forEach((k, v) -> {
            log.info("{}: {}", k, v); // 구글에 프로젝트를 등록하면서 지정한 'API 범위' 항목과 application-oauth.yml 설정 파일과 관련됨.
        });

        String email = null;

        if ("Google".equals(clientName)) {
            email = user.getAttribute("email");
        }

        log.info("email: {}", email);

        Member member = saveSocialMember(email);

        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.name())
                )
                        .collect(Collectors.toList()),
                user.getAttributes()
        );
        authMemberDTO.setName(member.getName());

        return authMemberDTO;
    }

    private Member saveSocialMember(String email) {

        // 기존에 동일한 이메일로 가입한 회원이 있는 경우에는 그대로 조회만
        Member member = memberAuthMapper.getAuthMemberByEmail(email);

        if (member != null) {
            return member;
        }

        // 없다는 회원 추가 패스워드는 1111 이름은 그냥 이메일 주소로(임의)
        member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode("1111"))
                .name(email)
                .fromSocial(true)
                .build();

        member.addMemberRole(MemberRole.USER);

        memberMapper.save(member);
        memberMapper.setMemberRoles(member);

        return member;
    }
}
