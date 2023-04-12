package project.shop.coupon.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import project.shop.coupon.type.CouponType;
import project.shop.coupon.type.LogType;
import project.shop.user.type.authType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "couponHistory")
public class CouponHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long logId;
    @ManyToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;
    private LogType type;
    private String detail;

    private long userId;
    private authType userAuth;
    @CreatedDate
    private LocalDateTime createdAt;
}
