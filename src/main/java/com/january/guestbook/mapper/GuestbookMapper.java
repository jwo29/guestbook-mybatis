package com.january.guestbook.mapper;

import com.january.guestbook.domain.Guestbook;
import com.january.guestbook.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GuestbookMapper {

    Guestbook findByGno(Long gno);

    List<Guestbook> findAll(PageRequestDTO requestDTO);

    long countAll();

    void insert(Guestbook guestbook);

    void update(Guestbook guestbook);

    void delete(Long gno);

}
