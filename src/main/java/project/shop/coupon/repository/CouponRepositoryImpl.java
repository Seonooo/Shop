package project.shop.coupon.repository;


import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.shop.coupon.domain.Coupon;
import project.shop.coupon.domain.QCoupon;

import java.util.List;
import javax.persistence.EntityManager;

import static project.shop.coupon.domain.QCoupon.coupon;

@Repository
public class CouponRepositoryImpl implements CouponRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CouponRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Coupon> getByKorCode() {
        return queryFactory
                .select(coupon)
                .from(coupon)
                .where(Expressions.booleanTemplate("REGEXP_LIKE({0}, '{1}')", coupon.Code, "[가-힣]"))
                .fetch();
    }
}