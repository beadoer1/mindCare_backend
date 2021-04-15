package com.sparta.mindcare.controller;

import com.sparta.mindcare.config.JwtTokenProvider;
import com.sparta.mindcare.controllerReturn.UserReturn;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/api/signup")
    public Long join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .username(user.get("username"))
                .password(passwordEncoder.encode(user.get("password")))
                .phone(user.get("phone"))
                .build()).getId();
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
}
