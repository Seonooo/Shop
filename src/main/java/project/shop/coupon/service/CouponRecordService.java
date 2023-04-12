package project.shop.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shop.coupon.domain.Coupon;
import project.shop.coupon.domain.CouponRecord;
import project.shop.coupon.dto.CouponRecordDto;
import project.shop.coupon.dto.CouponRequestDto;
import project.shop.coupon.dto.CouponResponseDto;
import project.shop.coupon.exception.ResourceNotFoundException;
import project.shop.coupon.repository.CouponHistoryRepository;
import project.shop.coupon.repository.CouponRecordRepository;
import project.shop.coupon.repository.CouponRepository;
import project.shop.coupon.type.CouponStatus;
import project.shop.user.domain.Member;
import project.shop.user.service.MemberService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponRecordService {
    private final CouponRecordRepository couponRecordRepository;
    private final MemberService memberService;
    private final CouponService couponService;
    /**
     * 쿠폰 발급 요청
     *
     */
    @Transactional
    public CouponRecordDto.Response create(long couponId) {
        Member member = memberService.getMemberInfo();

        // Check if the member already downloaded the coupon
        List<CouponRecord> existingRecords = couponRecordRepository.findByUserIdAndCouponId(member.getUserId(), couponId);
        if (!existingRecords.isEmpty()) {
            throw new RuntimeException("Coupon has already been downloaded by the member.");
        }

        // Create a new coupon record and save it
        CouponRecord record = CouponRecord.builder()
                .userId(member.getUserId())
                .status(CouponStatus.UNUSED)
                .couponId(couponId)
                .build();

        couponRecordRepository.save(record);

        return CouponRecordDto.Response.builder()
                .recordId(record.getRecordId())
                .CouponId(couponId)
                .userId(member.getUserId())
                .build();
    }

    /**
     * 해당 유저가 가지고 있는 쿠폰 발급 내역에 대한 요청
     */
    @Transactional
    public List<CouponRecord> getAll(){
        Member member = memberService.getMemberInfo();

        return couponRecordRepository.findByUserId(member.getUserId());
    }
}