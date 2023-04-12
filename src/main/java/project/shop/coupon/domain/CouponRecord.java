package project.shop.coupon.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import project.shop.coupon.type.CouponStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "couponRecord")
public class CouponRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long recordId;
//    @ManyToOne
//    @JoinColumn(name = "couponId")
    private long couponId;
    private long userId;
    @CreatedDate
    private LocalDateTime createdAt;
    private CouponStatus status; // use, unused, expired
}
