package com.sparta.springweekone.bulletinboard.domain;

import com.sparta.springweekone.bulletinboard.dto.*;
import com.sparta.springweekone.bulletinboard.entity.BulletinBoard;
import com.sparta.springweekone.bulletinboard.repository.BulletinBoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BulletinBoardServiceTest {

    @Autowired
    BulletinBoardService bulletinBoardService;

    @Autowired
    BulletinBoardRepository bulletinBoardRepository;

    @BeforeEach
    void beforeEach() {
        BulletinBoardForm bulletinBoardForm1 = new BulletinBoardForm("123a","자기소개","나는 포도입니다.","포도");
        BulletinBoardForm bulletinBoardForm2 = new BulletinBoardForm("123a","자기소개","나는 자몽입니다.","자몽");

        bulletinBoardService.create(bulletinBoardForm1);
        bulletinBoardService.create(bulletinBoardForm2);
    }
    @AfterEach
    void afterEach() {
        bulletinBoardRepository.deleteAll();
    }

    @DisplayName("게시글은 고유한 ID를 가져야 합니다.")
    @Test
    void hasUniqueId() {
;
        List<BulletinBoardDto> boardDtoList = bulletinBoardService.readAll();

        assertThat(boardDtoList.get(0).getId()).isNotEqualTo(boardDtoList.get(1).getId());
    }

    @DisplayName("게시글을 저장할 수 있습니다.")
    @Test
    void saveOK() {
        BulletinBoardForm bulletinBoardForm = new BulletinBoardForm("!234","타이틀","안녕 나는 감자","감자");
        BulletinBoardDto boardDto = bulletinBoardService.create(bulletinBoardForm);

        assertThat(boardDto.getNickname()).isEqualTo("감자");
    }

    @DisplayName("게시글 비밀번호가 일치하면 게시글이 수정됩니다.")
    @Test
    void UpdateSuccess() {
        BulletinBoard findBoard = bulletinBoardRepository.findByNickname("자몽");

        BulletinBoardForm updateForm = new BulletinBoardForm("123a","자기소개2","안녕 나는 자몽","자몽");
        bulletinBoardService.update(findBoard.getId(), updateForm);

        BulletinBoard updateBoard = bulletinBoardRepository.findByNickname("자몽");

        assertThat(updateBoard.getTitle()).isEqualTo("자기소개2");
    }

    @DisplayName("게시글 비밀번호가 일치하지 않으면 게시글을 수정할 수 없으며 401에러를 클라이언트에게 보냅니다.")
    @Test
    void updateFail() {
        BulletinBoard findBoard = bulletinBoardRepository.findByNickname("자몽");

        //비밀번호가 틀린 경우
        BulletinBoardForm updateForm = new BulletinBoardForm("12123123","자기소개2","안녕 나는 자몽","자몽");

        Message message = bulletinBoardService.update(findBoard.getId(), updateForm);

        Assertions.assertThat(message.getSuccess()).isEqualTo(false);

        BulletinBoard updateBoard = bulletinBoardRepository.findByNickname("자몽");
        Assertions.assertThat(updateBoard.getTitle()).isEqualTo("자기소개");
    }

    @DisplayName("비밀번호가 일치하면 게시글이 삭제되면 해당 ID의 컬럼들의 값은 null입니다.")
    @Test
    void deleteSuccess() {
        BulletinBoard findBoard = bulletinBoardRepository.findByNickname("자몽");

        ResultDto result = bulletinBoardService.delete(findBoard.getId(), new PasswordDto("123a"));
        Assertions.assertThat(result.isSuccess()).isTrue();

        Assertions.assertThatThrownBy(
                ()-> bulletinBoardService.readOne(findBoard.getId())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비밀번호가 일치하지 않으면 게시글이 삭제되지 않습니다.")
    @Test
    void deleteFail() {
        BulletinBoard findBoard = bulletinBoardRepository.findByNickname("자몽");

        // 비밀번호 틀린 경우
        ResultDto result = bulletinBoardService.delete(findBoard.getId(), new PasswordDto("!2324"));
        Assertions.assertThat(result.isSuccess()).isFalse();

        Assertions.assertThat(findBoard.getNickname()).isEqualTo("자몽");
    }
}