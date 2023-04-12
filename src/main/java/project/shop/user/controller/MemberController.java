package project.shop.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop.global.response.Response;
import project.shop.user.domain.Member;
import project.shop.user.dto.*;
import project.shop.user.service.MemberService;
import project.shop.user.type.authType;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Member Controller", tags = "Members")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;
    private final Response response;

    @ApiOperation(value = "유저 조회", notes = "유저를 전체 조회한다.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<MemberResponseDto.MemberInfo>> users() {
        List<Member> findMembers = memberService.findMembers();

        List<MemberResponseDto.MemberInfo> collect = findMembers.stream()
                .map(m -> new MemberResponseDto.MemberInfo(m.getEmail(), m.getName(), authType.ROLE_USER)) // role에 대한 처리..
                .collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> register(@Valid @RequestBody MemberRequestDto.SignUp signupRequestDto) throws Exception {
        return memberService.signUp(signupRequestDto);
    }

    @ApiOperation(value = "로그인", notes = "로그인을 한다.")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody MemberRequestDto.Login loginRequestDto) {
        return memberService.login(loginRequestDto);
    }
    @ApiOperation(value = "로그아웃", notes = "로그아웃을 한다.")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated MemberRequestDto.Logout logout, Errors errors) {
        // validation check

        return memberService.logout(logout);
    }

    @ApiOperation(value = "토큰 재발급", notes = "리프레시 토큰으로 새로운 액세스 토큰을 발급 받는다.")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated MemberRequestDto.Reissue reissue, Errors errors) {
        // validation check
//        if (errors.hasErrors()) {
//            return response.invalidFields(Helper.refineErrors(errors));
//        }
        return memberService.reissue(reissue);
    }

    @ApiOperation(value = "어드민 권한", notes = "어드민 권한을 추가 한다.")
    @GetMapping("/authority")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> authority() {
        log.info("ADD ROLE_ADMIN");
        return memberService.authority();
    }
}