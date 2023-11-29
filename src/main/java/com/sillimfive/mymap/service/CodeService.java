package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Code;
import com.sillimfive.mymap.repository.AlarmRepository;
import com.sillimfive.mymap.repository.CodeQuertdslRepository;
import com.sillimfive.mymap.repository.CodeRepository;
import com.sillimfive.mymap.web.dto.CategoryDto;
import com.sillimfive.mymap.web.dto.CategorySearch;
import com.sillimfive.mymap.web.dto.CodeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CodeService {

    private final CodeQuertdslRepository codeQuertdslRepository;

    public List<CodeResponseDto> findCodeTypeCodeList(String codeType) {
        List<CodeResponseDto> codeList = codeQuertdslRepository.findCodeTypeCodeList(codeType);
        return codeList;
    }
}
