package kr.co.togetherbegin.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinRequestDto {
    private String userName;
    private String password;
}
