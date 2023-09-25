package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     *  로그인
     * **/
    @Transactional
    public Long Login(User user){

        boolean duplicateUserCheckFlag = validateDuplicateUser(user); //중복 회원 검증
        if(duplicateUserCheckFlag){

        }else{
            userRepository.save(user);
        }
        return user.getId();
    }

    private boolean validateDuplicateUser(User user){
        List<User> findUsers = userRepository.findByLoginId(user.getLoginId());
        if(!findUsers.isEmpty()) {
            return false;
        }else{
            return true;
        }
    }



}
