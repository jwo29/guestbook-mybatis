package com.january.guestbook.security.service;

import com.january.guestbook.domain.Member;
import com.january.guestbook.mapper.MemberAuthMapper;
import com.january.guestbook.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@Service // @Service 어노테이션으로 인해 Bean으로 등록된다면, 이를 자동으로 스프링 시큐리티에서 UserDetailsService로 인식한다.
@RequiredArgsConstructor
public class MemberUserDetailService implements UserDetailsService {

    private final MemberAuthMapper memberAuthMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("MemberUserDetailService.loadUserByUsername() " + username);

        Member member = memberAuthMapper.getAuthMemberByEmail(username);

        if (member == null) {
            throw new UsernameNotFoundException("Check Email or Social");
        }

        log.info("------------------------------");
        log.info("Member: " +  member);

        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet())
        );

        authMemberDTO.setName(member.getName());
        authMemberDTO.setFromSocial(member.isFromSocial());

        return authMemberDTO;
    }
}
