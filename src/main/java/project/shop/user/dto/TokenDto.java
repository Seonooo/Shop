package project.shop.user.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String grantType; // JWT 대한 인증 타입 ( ex: Bearer )
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationTime;
}

