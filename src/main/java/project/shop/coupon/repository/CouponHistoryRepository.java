package project.shop.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.coupon.domain.CouponHistory;
import project.shop.coupon.domain.CouponRecord;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {
}
