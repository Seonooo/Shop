package project.shop.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shop.coupon.domain.Coupon;
import project.shop.coupon.domain.CouponHistory;
import project.shop.coupon.dto.CouponRequestDto;
import project.shop.coupon.dto.CouponResponseDto;
import project.shop.coupon.exception.ResourceNotFoundException;
import project.shop.coupon.repository.CouponHistoryRepository;
import project.shop.coupon.repository.CouponRepository;
import project.shop.user.domain.Member;
import project.shop.user.service.MemberService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final MemberService memberService;
    private final CouponRepository couponRepository;
    private final CouponHistoryRepository couponHistoryRepository;

    @Transactional
    public CouponResponseDto create(CouponRequestDto couponRequestDto) {
        couponRepository.findById(couponRequestDto.getCouponId())//.isPresent()
                .orElseThrow(() -> new ResourceNotFoundException("이미 존재하는 쿠폰 입니다. " + couponRequestDto.getCouponId()));

        Coupon coupon = couponRequestDto.toEntity();
        couponRepository.save(coupon);

        // 히스토리 업데이트

        return coupon.toResponse();
    }

    @Transactional
    public CouponResponseDto update(Long id, CouponRequestDto couponRequestDto) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id " + id));

        coupon.update(couponRequestDto);

        // 히스토리 저장

        return coupon.toResponse();
    }

    //쿠폰 삭제
    @Transactional
    public void delete(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id " + id));
        couponRepository.deleteById(id);

        Member member = memberService.getMemberInfo();
        updateTransactionLog(coupon, member);
    }



    // 특정 쿠폰에 할당된 상품 리스트 반환
    public void getAppliedProducts(Long id){

        // 1. 쿠폰id 기준 적용 가능 상품그룹id 조회
        // 2. 상품그룹id로 상품 DB에서 상품 IDS 반환 ( 상품명, 가격 .. )
        var list = new ArrayList<>(3){};
        list.add("productId1");
        list.add("productId2");
    }

    public void getAppliedProductInfo(Long productId, Long couponId){
        // 1. 상품 id로 상품 가격 가져오고, 쿠폰id로 쿠폰 type, rate 등을 가져옴
    }

    public List<CouponResponseDto> getAll(String codeType) {
        List<Coupon> entityCoupons = couponRepository.findAll();
        return entityCoupons.stream()
                .map(Coupon::toResponse)
                .collect(Collectors.toList());
    }

    public CouponResponseDto get(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id " + id));
        return coupon.toResponse();
    }

    //쿠폰코드에 한글이 포함된 쿠폰만 조회 ( 제거 )
    public List<CouponResponseDto> getByKorCode(String codeType) {
        if (codeType.equals("kor")) {
            List<Coupon> list = couponRepository.getByKorCode();
            return list.stream()
                    .map(Coupon::toResponse)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public void updateTransactionLog(Coupon coupon, Member member){
        // 히스토리 저장
        couponHistoryRepository.save(
                        CouponHistory.builder()
                                .coupon(coupon)
                                .detail("") // diff 값을 어떻게 json처리?
                                .userId(member.getUserId())
//                                .userAuth(member.getAuthorities())
                                .build()
        );
    }
}