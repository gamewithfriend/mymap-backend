package com.sillimfive.mymap.web;

import com.sillimfive.mymap.service.CodeService;
import com.sillimfive.mymap.web.dto.CodeResponseDto;
import com.sillimfive.mymap.web.dto.MyMapResponse;
import com.sillimfive.mymap.web.dto.alarm.AlarmResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Code", description = "Code API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/code")
public class CodeController {

    private final CodeService codeService;

    @Operation(summary = "codeType별 codeList", description = "admin get codeList ")
    @GetMapping
    public MyMapResponse<List<CodeResponseDto>> findCodeTypeCodeList(@RequestParam String codeType) {

        List<CodeResponseDto> codeList = codeService.findCodeTypeCodeList(codeType);
        return MyMapResponse.create()
                .succeed()
                .buildWith(codeList);
    }
}
