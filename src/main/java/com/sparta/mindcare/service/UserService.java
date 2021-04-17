package com.sparta.mindcare.service;

import com.sparta.mindcare.dto.UserDto;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void registerUser(UserDto userDto) {
        String pattern = "^[0-9]*$";
        String username = userDto.getUsername();


        if(username.length()<3){
            throw new IllegalArgumentException("닉네임은 최소 3자 이상으로 구성돼야 합니다.");
        }

//        else if(!Pattern.matches(pattern, username)){
//            throw new IllegalArgumentException("닉네임은 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성돼야 합니다.");
//        }

        if(userDto.getPassword().length()<4){
            throw new IllegalArgumentException("패스워드는 최소 4자 이상으로 구성돼야 합니다.");
        }
        else if(userDto.getPassword().contains((username))){
            throw new IllegalArgumentException("패스워드에 닉네임이 포함되어있습니다.");
        }

        if(!Pattern.matches(pattern, userDto.getPhone())){
            throw new IllegalArgumentException("전화번호는 숫자(0~9)로 구성돼야 합니다.");
        }
        // 닉네임 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }

        userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phone(userDto.getPhone())
                .build());
    }
}
