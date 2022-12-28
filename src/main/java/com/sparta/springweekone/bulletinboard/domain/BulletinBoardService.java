package com.sparta.springweekone.bulletinboard.domain;
import com.sparta.springweekone.bulletinboard.dto.*;
import com.sparta.springweekone.bulletinboard.entity.BulletinBoard;
import com.sparta.springweekone.bulletinboard.repository.BulletinBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BulletinBoardService {

    private final BulletinBoardRepository bulletinBoardRepository;

    public BulletinBoardDto create(BulletinBoardForm boardForm) {
        BulletinBoard board = new BulletinBoard(boardForm);
        BulletinBoard saveBoard = bulletinBoardRepository.save(board);

        return new BulletinBoardDto(saveBoard);
    }

    public List<BulletinBoardDto> readAll() {
        List<BulletinBoard> boards = bulletinBoardRepository.findAllByOrderByCreateAtDesc();

        return boards.stream().map(bulletinBoard -> new BulletinBoardDto(bulletinBoard)).collect(Collectors.toList());
    }

    public BulletinBoardDto readOne(Long id) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return new BulletinBoardDto(board);
    }

    public ResultDto delete(Long id, PasswordDto passwordDto) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (isNotSame(passwordDto.getPassword(), board.getPassword())) {
            return new ResultDto(false);
        }

        bulletinBoardRepository.deleteById(id);
        return new ResultDto(true);
    }

    @Transactional
    public Message update(Long id, BulletinBoardForm boardForm) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (isNotSame(boardForm.getPassword(), board.getPassword())) {
            return new Message(false, null);
        }

        board.update(boardForm);

        return new Message(true, new BulletinBoardDto(board));
    }

    private boolean isNotSame(String passwordOfDto, String passwordOfEntity) {
        return !passwordOfEntity.equals(passwordOfDto);
    }

}
