package com.sillimfive.mymap.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sillimfive.mymap.web.dto.CodeResponseDto;
import com.sillimfive.mymap.web.dto.alarm.AlarmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sillimfive.mymap.domain.QAlarm.alarm;
import static com.sillimfive.mymap.domain.QCode.code;

@Repository
@RequiredArgsConstructor
public class CodeQuertdslRepository {
    private final JPAQueryFactory queryFactory;

    public List<CodeResponseDto> findCodeTypeCodeList(String codeType) {
        List<CodeResponseDto> resultList = queryFactory
                .select(Projections.fields(CodeResponseDto.class,
                        code.id,
                        code.value
                ))
                .from(code)
                .where(code.codeType.eq(codeType))
                .fetch();
        return resultList;
    }
}
