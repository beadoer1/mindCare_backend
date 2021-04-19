package com.sparta.mindcare.controller;

import com.sparta.mindcare.config.JwtTokenProvider;
import com.sparta.mindcare.controllerReturn.ResultReturn;
import com.sparta.mindcare.controllerReturn.UserReturn;
import com.sparta.mindcare.dto.UserDto;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.UserRepository;
import com.sparta.mindcare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;

    // 회원가입
    @PostMapping("/api/signup")
    public ResultReturn join(@RequestBody UserDto userDto) {


        try{
            userService.registerUser(userDto);
        }
        catch(IllegalArgumentException e){
             //에러 발생 시 이 쪽으로 이동되고, 에러메시지를 model 의 "error" 값으로 전달
            return new ResultReturn(false, e.getMessage());
        }

        return new ResultReturn(true, "마이케어 회원가입이 완료되었습니다.");
//        return userRepository.save(User.builder()
//                .username(user.get("username"))
//                .password(passwordEncoder.encode(user.get("password")))
//                .phone(user.get("phone"))
//                .build()).getId();
    }

    // 로그인
    @PostMapping("/api/login")
    public UserReturn login(@RequestBody Map<String, String> user) {

        UserReturn userReturn = new UserReturn();

        try{
            User member = userRepository.findByUsername(user.get("username"))
                    .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));
            if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
            String token= jwtTokenProvider.createToken(member.getUsername());
            userReturn.setToken(token);
        }
        catch(IllegalArgumentException e){
            userReturn.setOk(false);
            userReturn.setMsg(e.getMessage());
            return userReturn;
        }

        userReturn.setOk(true);
        userReturn.setMsg("로그인이 완료되었습니다.");
        return userReturn;
    }

    @GetMapping("/api/logincheck")
    public ResultReturn loginCheck(@AuthenticationPrincipal User user) {
        if(user == null){
            return new ResultReturn(false,"로그인이 필요한 기능입니다.");
        }
        return new ResultReturn(true,"로그인 확인!");
    }

    @DeleteMapping("/api/user/{userId}")
    public Long delete(@PathVariable Long userId){
        userRepository.deleteById(userId);
        return userId;
    }
}
