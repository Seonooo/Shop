package project.shop.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.coupon.domain.CouponRecord;

import java.util.List;

public interface CouponRecordRepository extends JpaRepository<CouponRecord, Long> {
    List<CouponRecord> findByCouponId(long couponId);


    List<CouponRecord> findByUserIdAndCouponId(long userId, long couponId);

    List<CouponRecord> findByUserId(long usrId);
}
