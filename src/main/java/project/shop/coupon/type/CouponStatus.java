package project.shop.coupon.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponStatus {

    // member 관점
    USED("사용"),
    UNUSED("미사용"),
    EXPIRATION("기한만료"),

    // admin 관점
    ACTIVE("활성"),
    DELETE("삭제");

    private final String status;
}
