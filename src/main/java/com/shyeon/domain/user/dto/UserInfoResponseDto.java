package com.shyeon.domain.user.dto;

import com.shyeon.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserInfoResponseDto {

    private Long id;
    private String email;
    private String nickname;
    private LocalDateTime joinDate;
    private LocalDateTime lastUpdatedDate;

    public static UserInfoResponseDto from (User user) {
        return UserInfoResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .joinDate(user.getCreateDate())
                .lastUpdatedDate(user.getModifiedDate())
                .build();
    }
}
