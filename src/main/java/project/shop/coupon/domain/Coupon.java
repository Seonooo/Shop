package project.shop.coupon.domain;



import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.shop.coupon.dto.CouponRequestDto;
import project.shop.coupon.dto.CouponResponseDto;
import project.shop.coupon.type.CouponStatus;
import project.shop.coupon.type.CouponType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long couponId;
    @Column(nullable = false)
    private CouponType type;
    @Column(nullable = false)
    private String title;
    private String detail;

    @Column(nullable = false)
    private long item_type_id;
    @Column(nullable = false)
    private long discount;
    private long stock;
//    private long minimumPrice; // 최소 충족 가격 **
//    private long maxDiscountLimit; // 최대 할인 가격 **
    private CouponStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime expiredAt;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    public void update(CouponRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.item_type_id = requestDto.getItem_type_id();
        this.discount = requestDto.getDiscount();
        this.stock = requestDto.getStock();
        this.startedAt = requestDto.getStartedAt();
        this.expiredAt = requestDto.getExpiredAt();
        this.status = requestDto.getStatus();
    }

    public void delete() {
        this.status = CouponStatus.DELETE;
    }

    public CouponResponseDto toResponse() {
        return CouponResponseDto.builder()
                .couponId(couponId)
                .title(title)
                .discount(discount)
                .startedAt(startedAt)
                .expiredAt(expiredAt)
                .build();
    }


}
