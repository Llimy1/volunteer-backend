package com.maple.volunteer.controller.user;

import com.maple.volunteer.dto.common.CommonResponseDto;
import com.maple.volunteer.dto.common.ResultDto;
import com.maple.volunteer.dto.user.LogoutDto;
import com.maple.volunteer.dto.user.NewTokenDto;
import com.maple.volunteer.dto.user.TokenDto;
import com.maple.volunteer.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/maple/user")
public class UserController {

    private final UserService userService;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResultDto<TokenDto>> userLogin(@RequestParam("email") String email,
                                                           @RequestParam("role") String role) {

        CommonResponseDto<Object> login = userService.login(email, role);
        ResultDto<TokenDto> result = ResultDto.in(login.getStatus(), login.getMessage());
        result.setData((TokenDto) login.getData());

        return ResponseEntity.status(login.getHttpStatus()).body(result);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ResultDto<LogoutDto>> userLogout(@RequestHeader("Authorization") String accessToken){

        CommonResponseDto<Object> logout = userService.logout(accessToken);
        ResultDto<LogoutDto> result = ResultDto.in(logout.getStatus(), logout.getMessage());
        result.setData((LogoutDto) logout.getData());

        return ResponseEntity.status(logout.getHttpStatus()).body(result);
    }

    // 토큰 갱신
    @PostMapping("/newToken")
    public ResponseEntity<ResultDto<NewTokenDto>> renewToken(@RequestHeader("Authorization") String accessToken){

        CommonResponseDto<Object> renewToken = userService.renewToken(accessToken);
        ResultDto<NewTokenDto> result = ResultDto.in(renewToken.getStatus(), renewToken.getMessage());
        result.setData((NewTokenDto) renewToken.getData());

        return ResponseEntity.status(renewToken.getHttpStatus()).body(result);
    }
}
