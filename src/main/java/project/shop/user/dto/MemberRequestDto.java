package project.shop.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import project.shop.user.domain.Member;
import project.shop.user.type.authType;

import javax.validation.constraints.*;

public class MemberRequestDto {
    @Getter
    @Setter
    public static class SignUp {
        @NotBlank(message = "이메일을 입력해주세요")
        private String email;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min=2, message = "닉네임이 너무 짧습니다.")
        private String nickname;

        @NotNull(message = "나이를 입력해주세요")
        @Range(min = 0, max = 150)
        private int age;

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
                message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
        private String password;
        private String checkedPassword;

        private authType role;
    }


    @Getter
    @Setter
    public static class Login {
        @NotEmpty(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Getter
    @Setter
    public static class Reissue {
        @NotEmpty(message = "accessToken 을 입력해주세요.")
        private String accessToken;

        @NotEmpty(message = "refreshToken 을 입력해주세요.")
        private String refreshToken;
    }

    @Getter
    @Setter
    public static class Logout {
        @NotEmpty(message = "잘못된 요청입니다.")
        private String accessToken;

        @NotEmpty(message = "잘못된 요청입니다.")
        private String refreshToken;
    }
}
