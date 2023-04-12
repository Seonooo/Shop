package project.shop.coupon.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.shop.coupon.domain.Coupon;
import project.shop.coupon.type.CouponStatus;
import project.shop.coupon.type.CouponType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponRequestDto {
    @NotBlank(message = "쿠폰코드를 입력해주세요.")
    private long CouponId;
    @NotBlank(message = "쿠폰이름을 입력해주세요.")
    private String title;
    @NotBlank(message = "쿠폰 타입을 선택해주세요.") // 선택 여부 수정 가능한지
    private CouponType type;
    @NotBlank(message = "적용할 상품그룹 ID를 입력해주세요.")
    private long item_type_id; // 상품 그룹 id
    @NotNull(message = "할인가격 및 비율을 입력해주세요.")
    private long discount; // 비율 -> rate, 정액 -> 할인가격 **
    @NotNull(message = "쿠폰수량을 입력해주세요.")
    private long stock;
    @NotNull(message = "시작일을 입력해주세요.")
    private LocalDateTime startedAt;
    @NotNull(message = "만료일을 입력해주세요.")
    private LocalDateTime expiredAt;
    private CouponStatus status;

    // coupon to dto
    public  Coupon toEntity() {
        return Coupon.builder()
                .couponId(CouponId)
                .title(title)
                .type(type)
                .item_type_id(item_type_id)
                .discount(discount)
                .stock(stock)
                .startedAt(startedAt)
                .expiredAt(expiredAt)
                .build();
    }
}