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
}
