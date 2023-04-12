package project.shop.coupon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.shop.coupon.domain.CouponRecord;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CouponRecordDto {
//    @Getter
//    @Setter
//    public static class Request{
//        private long CouponId;
//    }

    @Getter
    @Setter
    @Builder
    public static class Response {
        private long recordId;
        private long userId;
        private long CouponId;
        private LocalDateTime createdAt;

    }



}
