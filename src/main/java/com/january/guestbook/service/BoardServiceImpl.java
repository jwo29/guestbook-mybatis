package com.january.guestbook.service;

import com.january.guestbook.domain.Board;
import com.january.guestbook.dto.BoardListDTO;
import com.january.guestbook.dto.BoardModifyDTO;
import com.january.guestbook.dto.PageRequestDTO;
import com.january.guestbook.dto.PageResultDTO;
import com.january.guestbook.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Guestbook 도메인의 비즈니스 로직을 담당하는 서비스
 *
 * <p>
 *     본 서비스는 현재 단순 CRUD 중심이며,
 *     구현체 교체 가능성이나 다형성이 요구되지 않아
 *     Service 인터페이스를 별도로 두지 않았다.
 *     향후 비즈니스 규칙이 복잡해지거나,
 *     외부 시스템 연동, 테스트 대역(Mock) 필요성이 생길 경우
 *     인터페이스 분리를 검토할 수 있다.
 *
 *     분리했다!
 * </p>
 */
@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    /**
     * 방명록 등록
     * @param board 등록할 방명록 정보
     * @return 등록된 게시글 번호(gno)
     */
    @Override
    @Transactional
    public Long register(Board board) {
        log.info("Register board: {}", board);

        boardMapper.insert(board);

        // MyBatis useGeneratedKeys 설정으로 자동 생성된 키가 board 객체에 설정됨
        return board.getGno();
    }

    @Override
    public Board read(Long gno) {
        return boardMapper.findByGno(gno);
    }

    @Override
    public void modify(BoardModifyDTO boardModifyDTO) {
        Board update = boardMapper.findByGno(boardModifyDTO.getGno());

        if (update != null) {
            update.changeTitle(boardModifyDTO.getTitle());
            update.changeContent(boardModifyDTO.getContent());

            boardMapper.update(update);
        }
    }

    @Override
    public void delete(Long gno) {
        boardMapper.deleteByGno(gno);
    }

    /**
     * 페이징 처리된 방명록 목록 조회
     * @param requestDTO 페이지 요청 정보 (page, size)
     * @return 페이징 처리된 결과 (데이터 목록, 전체 개수, 페이지 정보)
     */
    @Override
    @Transactional(readOnly = true)
    public PageResultDTO<BoardListDTO> boardList(PageRequestDTO requestDTO) {
        log.info("Get board list - page: {}, size: {}", requestDTO.getPage(), requestDTO.getSize());

        // 전체 게시글 수 조회
        long totalCount = boardMapper.countAll(requestDTO);
        log.info("Total count: {}", totalCount);

        // 페이징 처리된 목록 조회
        List<BoardListDTO> boardList = boardMapper.findAll(requestDTO);
        log.info("Retrieved {} boards", boardList.size());

        // PageResultDTO 생성 및 반환
        return new PageResultDTO<>(
                boardList,
                totalCount,
                requestDTO.getPage(),
                requestDTO.getSize()
        );
    }
}
