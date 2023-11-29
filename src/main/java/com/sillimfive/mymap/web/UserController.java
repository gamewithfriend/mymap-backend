package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.service.UserService;
import com.sillimfive.mymap.web.dto.MyMapResponse;
import com.sillimfive.mymap.web.dto.user.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "user API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "개인정보 확인", description = "todo: implementation")
    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<UserInfo> userDetails(Authentication authentication) {


        User loginUser = (User) authentication.getPrincipal();
        loginUser = userService.findById(loginUser.getId());

        UserInfo userInfo = new UserInfo(loginUser);

        return MyMapResponse.create()
                .succeed()
                .buildWith(userInfo);
    }

    @Operation(summary = "개인정보 변경", description = "현재는 nickname 변경만 존재 ")
    @PutMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> update(@PathVariable("changeNickname") String changeNickname,Authentication authentication) {
        User loginUser = (User) authentication.getPrincipal();
        Long userId = loginUser.getId();
        userService.editUser(userId,changeNickname);
        return MyMapResponse.create()
                .succeed()
                .buildWith(((User) authentication.getPrincipal()).getId());
    }
}