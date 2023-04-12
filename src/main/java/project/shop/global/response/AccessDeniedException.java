package project.shop.global.response;
import lombok.Getter;

@Getter
public class AccessDeniedException extends RuntimeException {
    private final ErrorCode errorCode;
    private String message;

    public AccessDeniedException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}