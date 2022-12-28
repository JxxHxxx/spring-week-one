package com.sparta.springweekone.bulletinboard.controller;

import com.sparta.springweekone.bulletinboard.domain.BulletinBoardService;
import com.sparta.springweekone.bulletinboard.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BulletinBoardController {

    private final BulletinBoardService bulletinBoardService;

    // 게시글 작성
    @PostMapping("/bulletin-boards")
    public BulletinBoardDto write(@RequestBody BulletinBoardForm boardForm) {
        return bulletinBoardService.create(boardForm);
    }

    // 전체 게시글 조회
    @GetMapping("/bulletin-boards")
    public List<BulletinBoardDto> readAll() {
        return bulletinBoardService.readAll();
    }
    // 선택 게시글 조회
    @GetMapping("/bulletin-boards/{id}")
    public BulletinBoardDto readOne(@PathVariable Long id) {
        return bulletinBoardService.readOne(id);
    }

    // 선택 게시글 수정
    @PatchMapping("/bulletin-boards/{id}")
    public ResponseEntity<Message> updateV2(@PathVariable Long id, @RequestBody BulletinBoardForm boardForm) {
        return bulletinBoardService.updateV2(id, boardForm);
    }

    // 선택 게시글 삭제
    @DeleteMapping("/bulletin-boards/{id}")
    public ResultDto remove(@PathVariable Long id, @RequestBody PasswordDto passwordDto) {
        return bulletinBoardService.delete(id, passwordDto);
    }
}
