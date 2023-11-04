package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    //리프레시토큰 을 위한 유저 id 검색 서비스 추가
    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));
    }

    //OAuth2 success핸들러 추가를 위한 메서드 추가
    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다"));
    }
}
