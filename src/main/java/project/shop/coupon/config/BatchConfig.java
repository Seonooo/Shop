package project.shop.coupon.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.shop.coupon.domain.Coupon;
import project.shop.coupon.domain.CouponRecord;
import project.shop.coupon.dto.CouponRequestDto;
import project.shop.coupon.repository.CouponRecordRepository;
import project.shop.coupon.repository.CouponRepository;
import project.shop.coupon.service.CouponService;
import project.shop.coupon.type.CouponStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory; // 생성자 DI 받음
    private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음
    private final CouponRepository couponRepository;
    private final CouponRecordRepository couponRecordRepository;
    private final CouponService couponService;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step())
                .build();
    }

    /**
     * 1. coupon ) 실행시점 기준 expiredAt이 전날이고, status가 active라면 delete로 업데이트한다.
     * 2. couponRecord ) 위 couponId로 조회시 status가 unuse라면 expired로 업데이트한다. ( 한번에 findall ( couponids )가 더 나을지는 확인 필요 )
     * 3. couponHistory ) 해당 couponId로 expired 로그 추가한다.
     */
    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step1");

                    List<Coupon> expiredCoupons = couponRepository.findByExpiredAtLessThan(LocalDateTime.now());

                    for (Coupon coupon : expiredCoupons) {

                        if (coupon.getStatus() == CouponStatus.ACTIVE) {
                            couponService.update(coupon.getCouponId(), CouponRequestDto.builder()
                                    .status(CouponStatus.DELETE)
                                    .build());
                        }

                        List<CouponRecord> couponRecords = couponRecordRepository.findByCouponId(coupon.getCouponId())
                                .stream()
                                .filter(couponRecord -> couponRecord.getStatus() == CouponStatus.UNUSED)
                                .collect(Collectors.toList());

                        couponRecords.forEach(couponRecord -> {
                            couponRecord.setStatus(CouponStatus.DELETE);
                            couponRecordRepository.save(couponRecord);
                        });
                    }


                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}