package com.sparta.springweekone.bulletinboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BulletinBoardForm {

    private String password;
    private String title;
    private String mainText;
    private String nickname;

    public BulletinBoardForm(String password, String title, String mainText, String nickname) {
        this.password = password;
        this.title = title;
        this.mainText = mainText;
        this.nickname = nickname;
    }

    public BulletinBoardForm(String title, String mainText, String nickname) {
        this.title = title;
        this.mainText = mainText;
        this.nickname = nickname;
    }

    public BulletinBoardForm(String password) {
        this.password = password;
    }
}
