package project.shop.coupon.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.shop.coupon.domain.Coupon;
import project.shop.coupon.type.CouponStatus;
import project.shop.coupon.type.CouponType;

import java.time.LocalDateTime;
@Getter
@Builder
public class CouponResponseDto {
    private long couponId;
    private String title;
    private long discount;
    private LocalDateTime startedAt;
    private LocalDateTime expiredAt;

    public static CouponResponseDto fromEntity(Coupon coupon){
        return CouponResponseDto.builder()
                .couponId(coupon.getCouponId())
                .title(coupon.getTitle())
                .discount(coupon.getDiscount())
                .startedAt(coupon.getStartedAt())
                .expiredAt(coupon.getExpiredAt())
                .build();
    }
}
