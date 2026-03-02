package com.january.guestbook.mapper;

import com.january.guestbook.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberAuthMapper {
    Member getAuthMemberByEmail(String email);
}
