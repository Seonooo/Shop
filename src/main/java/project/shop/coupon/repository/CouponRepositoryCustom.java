package project.shop.coupon.repository;

import project.shop.coupon.domain.Coupon;

import java.util.List;

public interface CouponRepositoryCustom {
    List<Coupon> getByKorCode();
}

