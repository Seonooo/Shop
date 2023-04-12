package project.shop.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    InvalidInputValueException(400,  " Invalid Input Value", HttpStatus.BAD_REQUEST),
    DuplicateException(400, "Username is Duplication", HttpStatus.BAD_REQUEST),
    UsernameOrPasswordNotFoundException (400, "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    BusinessException (400, "요청이 잘못되었습니다", HttpStatus.BAD_REQUEST),
    InternalServerError (400, "서버에서 에러가 발생하였습니다.", HttpStatus.BAD_REQUEST),

    ForbiddenException (403, "해당 요청에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    UNAUTHORIZEDException (401, "로그인 후에 이용가능합니다.", HttpStatus.UNAUTHORIZED),
    ExpiredJwtException (444, "기존 토큰이 만료 되었습니다.", HttpStatus.UNAUTHORIZED),
    InvalidRefreshToken(444, "다시 로그인해주세요", HttpStatus.UNAUTHORIZED),
    ReLogin (445, "다시 로그인해주세요", HttpStatus.UNAUTHORIZED),
    ;

    private int code;
    private String message;
    private HttpStatus status;
}