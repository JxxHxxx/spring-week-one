package com.sparta.springweekone.bulletinboard.domain;
import com.sparta.springweekone.bulletinboard.dto.*;
import com.sparta.springweekone.bulletinboard.repository.BulletinBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulletinBoardService {

    private final BulletinBoardRepository bulletinBoardRepository;
//    private final PasswordEncoder passwordEncoder;


    public BulletinBoardDto create(BulletinBoardForm boardForm) {
//        String encryptedPassword = passwordEncoder.encrypt(boardForm.getPassword());

        BulletinBoard board = new BulletinBoard(boardForm, boardForm.getPassword());
        BulletinBoard saveBoard = bulletinBoardRepository.save(board);

        return new BulletinBoardDto(saveBoard);

    }
    public List<BulletinBoardDto> readAll() {
        List<BulletinBoard> boards = bulletinBoardRepository.findAllByOrderByCreateAtDesc();

        return boards.stream().map(bulletinBoard -> new BulletinBoardDto(bulletinBoard)).collect(Collectors.toList());
    }

    public BulletinBoardDto readOne(Long id) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        BulletinBoardDto boardDto = new BulletinBoardDto(board);

        return boardDto;
    }
    public ResultDto delete(Long id, PasswordDto passwordDto) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow();

        if (isNotSame(passwordDto.getPassword(), board.getPassword())) {
            return new ResultDto(false);
        }
        bulletinBoardRepository.deleteById(id);
        return new ResultDto(true);

    }

    @Deprecated
    @Transactional
    public BulletinBoardDto update(Long id, BulletinBoardForm boardForm) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow();
        if (isNotSame(boardForm.getPassword(), board.getPassword())) {
            return null;
        }

        board.update(boardForm);
        return new BulletinBoardDto(board);
    }

    @Transactional
    public ResponseEntity<Message> updateV2(Long id, BulletinBoardForm boardForm) {
        BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if (isNotSame(boardForm.getPassword(), board.getPassword())) {
            Message message = new Message(false, null);
            return new ResponseEntity<>(message, headers, HttpStatus.UNAUTHORIZED);
        }

        board.update(boardForm);

        Message message = new Message(true, new BulletinBoardDto(board));
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    private boolean isNotSame(String passwordOfDto, String passwordOfEntity) {
//        String encryptedPassword = passwordEncoder.encrypt(passwordOfDto);
        return !passwordOfEntity.equals(passwordOfDto);
    }

}
