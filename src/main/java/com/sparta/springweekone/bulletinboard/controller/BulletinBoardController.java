package com.sparta.springweekone.bulletinboard.controller;

import com.sparta.springweekone.bulletinboard.domain.BulletinBoardService;
import com.sparta.springweekone.bulletinboard.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class BulletinBoardController {

    private final BulletinBoardService bulletinBoardService;

    public BulletinBoardController(BulletinBoardService bulletinBoardService) {
        this.bulletinBoardService = bulletinBoardService;
    }

    // 게시글 작성
    @PostMapping("/bulletin-board")
    public BulletinBoardDto write(@RequestBody BulletinBoardForm boardForm) {
        return bulletinBoardService.create(boardForm);
    }

    // 전체 게시글 조회
    @GetMapping("/bulletin-boards")
    public List<BulletinBoardDto> readAll() {
        return bulletinBoardService.readAll();
    }
    // 선택 게시글 조회
    @GetMapping("/bulletin-board/{id}")
    public BulletinBoardDto readOne(@PathVariable Long id) {
        return bulletinBoardService.readOne(id);
    }

    // 선택 게시글 수정
    @Deprecated
    @PatchMapping("/bulletin-board/{id}")
    public BulletinBoardDto update(@PathVariable Long id, @RequestBody BulletinBoardForm boardForm) {
        return bulletinBoardService.update(id, boardForm);
    }
    // 선택 게시글 수정 버전2
    @PatchMapping("/bulletin-board/v2/{id}")
    public ResponseEntity<Message> updateV2(@PathVariable Long id, @RequestBody BulletinBoardForm boardForm) {
        return bulletinBoardService.updateV2(id, boardForm);
    }

    // 선택 게시글 삭제
    @DeleteMapping("/bulletin-board/{id}")
    public ResultDto remove(@PathVariable Long id, @RequestBody PasswordDto passwordDto) {
        return bulletinBoardService.delete(id, passwordDto);
    }
}
