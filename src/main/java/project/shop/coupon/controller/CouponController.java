package project.shop.coupon.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.shop.coupon.dto.CouponRequestDto;
import project.shop.coupon.dto.CouponResponseDto;
import project.shop.coupon.service.CouponService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {
    private CouponService couponService;

    @GetMapping("")
    public ResponseEntity<?> getAllCoupons(
            @RequestParam(value = "codeType", required = false, defaultValue = "") @NotNull String codeType) {
        List<CouponResponseDto> result = couponService.getAll(codeType);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{couponId}")
    public CouponResponseDto getCouponById(@PathVariable Long couponId) {
        return couponService.get(couponId);
    }

    @PostMapping("")
    public ResponseEntity<?> createCoupon(@Valid @RequestBody CouponRequestDto couponRequestDto) {
        couponService.create(couponRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<?> updateCoupon(@PathVariable Long couponId, @Valid @RequestBody CouponRequestDto couponRequestDto) {
        CouponResponseDto result = couponService.update(couponId, couponRequestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long couponId) {
        couponService.delete(couponId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
