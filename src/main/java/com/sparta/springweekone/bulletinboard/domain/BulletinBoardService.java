package com.sparta.springweekone.bulletinboard.domain;
import com.sparta.springweekone.bulletinboard.dto.BulletinBoardDto;
import com.sparta.springweekone.bulletinboard.dto.ResultDto;
import com.sparta.springweekone.bulletinboard.repository.BulletinBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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

    public BulletinBoardDto create(BulletinBoardDto bulletinBoardDto) {
        log.info("BulletinBoardService - write");
        BulletinBoard bulletinBoard = new BulletinBoard(
                bulletinBoardDto.getNickname(),
                bulletinBoardDto.getPassword(),
                bulletinBoardDto.getTitle(),
                bulletinBoardDto.getMainText());

        bulletinBoardRepository.save(bulletinBoard);
        return bulletinBoardDto;

    }
    public List<BulletinBoardDto> readAll() {
        Sort sort = Sort.by("datetime").descending();
        List<BulletinBoard> boards = bulletinBoardRepository.findAll(sort);

        ArrayList<BulletinBoardDto> bulletinBoardDtos = new ArrayList<>();

        Stream<BulletinBoard> stream = boards.stream();
        stream.forEach(board -> bulletinBoardDtos.add(new BulletinBoardDto(
                board.getTitle(),
                board.getMainText(),
                board.getNickname())));

        return bulletinBoardDtos;
    }

    public BulletinBoardDto readOne(Long id) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow();
        BulletinBoardDto boardDto = new BulletinBoardDto(board.getTitle(), board.getMainText(), board.getNickname());

        return boardDto;
    }
    public ResultDto delete(Long id, BulletinBoardDto bulletinBoardDto) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow();
        log.info("db password = {}", board.getPassword());
        log.info("input password = {}", bulletinBoardDto);
        if (isCorrectPassword(bulletinBoardDto, board)) {
            log.info("비밀 번호가 일치합니다. 게시글을 삭제합니다.");
            bulletinBoardRepository.deleteById(id);
            return new ResultDto(true);
        }
        log.info("비밀 번호가 일치하지 않습니다.");
        return new ResultDto(false);

    }
    @Transactional
    public BulletinBoardDto update(Long id, BulletinBoardDto bulletinBoardDto) {
        BulletinBoard bulletinBoard = bulletinBoardRepository.findById(id).orElseThrow();
        log.info("bulletinBoard = {}", bulletinBoard.toString());
        if (!isCorrectPassword(bulletinBoardDto, bulletinBoard)) {
            return null;
        }

        bulletinBoard.update(bulletinBoardDto);
        log.info("bulletinBoard = {}", bulletinBoard.toString());
        BulletinBoardDto boardDto = new BulletinBoardDto(bulletinBoard.getTitle(), bulletinBoard.getNickname(), bulletinBoard.getMainText());
        return boardDto;
    }

    private static boolean isCorrectPassword(BulletinBoardDto bulletinBoardDto, BulletinBoard board) {
        return board.getPassword().equals(bulletinBoardDto.getPassword());
    }

}
