package project.shop.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.coupon.domain.Coupon;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> , CouponRepositoryCustom{
    List<Coupon> findByExpiredAtGreaterThan(LocalDateTime limit);

    List<Coupon> findByExpiredAtLessThan(LocalDateTime now);
}
