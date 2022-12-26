package com.sparta.springweekone.bulletinboard.controller;

import com.sparta.springweekone.bulletinboard.domain.BulletinBoardService;
import com.sparta.springweekone.bulletinboard.dto.BulletinBoardDto;
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
    public BulletinBoardDto write(@RequestBody BulletinBoardDto bulletinBoardDto) {
        log.info("BulletinBoardController - bulletin-board");
        log.info("dto {}", bulletinBoardDto.getNickname());
        bulletinBoardService.create(bulletinBoardDto);

        return bulletinBoardDto;
    }

    // 전체 게실글 조회
    @GetMapping("/bulletin-boards")
    public List<BulletinBoardDto> readAll() {
        return bulletinBoardService.readAll();
    }
    // 선택 게시글 조회
    @GetMapping("/bulletin-board/{id}")
    public BulletinBoardDto read(@PathVariable Long id) {
        return bulletinBoardService.readOne(id);
    }

    // 선택 게시글 수정
    @PatchMapping("/bulletin-board/{id}")
    public BulletinBoardDto update(@PathVariable Long id, @RequestBody BulletinBoardDto bulletinBoardDto) {
        BulletinBoardDto update = bulletinBoardService.update(id, bulletinBoardDto);
        return update;
    }
    // 선택 게시글 삭제
    @DeleteMapping("/bulletin-board/{id}")
    public String remove(@PathVariable Long id, @RequestBody BulletinBoardDto bulletinBoardDto) {
        bulletinBoardService.delete(id, bulletinBoardDto);
        return "success";
    }
}
