package com.sparta.springweekone.bulletinboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BulletinBoardDto {

    private String password;
    private String title;
    private String mainText;
    private String nickname;

    public BulletinBoardDto(String password, String title, String mainText, String nickname) {
        this.password = password;
        this.title = title;
        this.mainText = mainText;
        this.nickname = nickname;
    }

    public BulletinBoardDto(String title, String mainText, String nickname) {
        this.title = title;
        this.mainText = mainText;
        this.nickname = nickname;
    }

    public BulletinBoardDto(String password) {
        this.password = password;
    }
}
