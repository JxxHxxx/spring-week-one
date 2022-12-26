package com.sparta.springweekone.bulletinboard.domain;
import com.sparta.springweekone.bulletinboard.dto.BulletinBoardDto;
import com.sparta.springweekone.bulletinboard.dto.BulletinBoardForm;
import com.sparta.springweekone.bulletinboard.dto.PasswordDto;
import com.sparta.springweekone.bulletinboard.dto.ResultDto;
import com.sparta.springweekone.bulletinboard.repository.BulletinBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class BulletinBoardService {

    private final BulletinBoardRepository bulletinBoardRepository;

    public BulletinBoardService(BulletinBoardRepository bulletinBoardRepository) {
        this.bulletinBoardRepository = bulletinBoardRepository;
    }

    public BulletinBoardDto create(BulletinBoardForm boardForm) {
        log.info("BulletinBoardService - write");
        BulletinBoard board = new BulletinBoard(boardForm);
        BulletinBoard saveBoard = bulletinBoardRepository.save(board);

        return new BulletinBoardDto(saveBoard);

    }
    public List<BulletinBoardDto> readAll() {
//        Sort sort = Sort.by("modifiedAt").descending();
//        List<BulletinBoard> boards = bulletinBoardRepository.findAll(sort);

        List<BulletinBoard> boards = bulletinBoardRepository.findAllByOrderByCreateAtDesc();

        ArrayList<BulletinBoardDto> boardDtoList = new ArrayList<>();

        Stream<BulletinBoard> stream = boards.stream();
        stream.forEach(board -> boardDtoList.add(new BulletinBoardDto(board)));

        return boardDtoList;
    }

    public BulletinBoardDto readOne(Long id) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow();
        BulletinBoardDto boardDto = new BulletinBoardDto(board);

        return boardDto;
    }
    public ResultDto delete(Long id, PasswordDto passwordDto) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow();

        if (isNotSame(passwordDto.getPassword(), board.getPassword())) {
            log.info("비밀 번호가 일치하지 않습니다.");
            return new ResultDto(false);
        }
        log.info("비밀 번호가 일치합니다. 게시글을 삭제합니다.");
        return new ResultDto(true);

    }



    @Transactional
    public BulletinBoardDto update(Long id, BulletinBoardForm boardForm) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow();
        log.info("bulletinBoard = {}", board.toString());
        if (isNotSame(boardForm.getPassword(), board.getPassword())) {
            return null;
        }

        board.update(boardForm);
        log.info("bulletinBoard = {}", board.toString());
        return new BulletinBoardDto(board);
    }

    private static boolean isNotSame(String passwordOfDto, String passwordOfEntity) {
        return !passwordOfEntity.equals(passwordOfDto);
    }

}
