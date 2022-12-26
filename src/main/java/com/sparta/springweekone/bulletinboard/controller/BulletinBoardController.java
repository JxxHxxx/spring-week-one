package com.sparta.springweekone.bulletinboard.controller;

import com.sparta.springweekone.bulletinboard.domain.BulletinBoardService;
import com.sparta.springweekone.bulletinboard.dto.BulletinBoardForm;
import com.sparta.springweekone.bulletinboard.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class BulletinBoardController {

    private final BulletinBoardService bulletinBoardService;

    public BulletinBoardController(BulletinBoardService bulletinBoardService) {
        this.bulletinBoardService = bulletinBoardService;
    }

    // 게시글 작성
    @PostMapping("/bulletin-board")
    public BulletinBoardForm write(@RequestBody BulletinBoardForm bulletinBoardDto) {
        log.info("BulletinBoardController - bulletin-board");
        log.info("dto {}", bulletinBoardDto.getNickname());
        bulletinBoardService.create(bulletinBoardDto);

        return bulletinBoardDto;
    }

    // 전체 게실글 조회
    @GetMapping("/bulletin-boards")
    public List<BulletinBoardForm> readAll() {
        return bulletinBoardService.readAll();
    }
    // 선택 게시글 조회
    @GetMapping("/bulletin-board/{id}")
    public BulletinBoardForm read(@PathVariable Long id) {
        return bulletinBoardService.readOne(id);
    }

    // 선택 게시글 수정
    @PatchMapping("/bulletin-board/{id}")
    public BulletinBoardForm update(@PathVariable Long id, @RequestBody BulletinBoardForm bulletinBoardDto) {
        BulletinBoardForm update = bulletinBoardService.update(id, bulletinBoardDto);
        return update;
    }
    // 선택 게시글 삭제
    @DeleteMapping("/bulletin-board/{id}")
    public ResultDto remove(@PathVariable Long id, @RequestBody BulletinBoardForm bulletinBoardDto) {
        ResultDto result = bulletinBoardService.delete(id, bulletinBoardDto);
        return result;
    }
}
