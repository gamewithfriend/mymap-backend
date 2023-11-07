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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        // todo: refactoring
        User loginUser = (User) authentication.getPrincipal();
        loginUser = userService.findById(loginUser.getId());

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(loginUser.getEmail());
        userInfo.setNickName(loginUser.getNickName());
        userInfo.setImageId(loginUser.getImage().getId());
        userInfo.setImagePath(loginUser.getImage().getPath());

        return MyMapResponse.create()
                .succeed()
                .buildWith(userInfo);
    }

    @Operation(summary = "개인정보 변경", description = "todo: implementation - 현재는 nickname 변경만 제공")
    @PutMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> update(Authentication authentication) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(((User) authentication.getPrincipal()).getId());
    }
}