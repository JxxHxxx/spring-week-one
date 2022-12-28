package com.sparta.springweekone.bulletinboard.domain;

import com.sparta.springweekone.bulletinboard.dto.BulletinBoardForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class BulletinBoard extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String mainText;
    
    public BulletinBoard(BulletinBoardForm boardForm) {
        this.password = boardForm.getPassword();
        this.nickname = boardForm.getNickname();
        this.mainText = boardForm.getMainText();
        this.title = boardForm.getTitle();
    }

    public void update(BulletinBoardForm bulletinBoardDto) {
        this.nickname = bulletinBoardDto.getNickname();
        this.title = bulletinBoardDto.getTitle();
        this.mainText = bulletinBoardDto.getMainText();
    }
}
