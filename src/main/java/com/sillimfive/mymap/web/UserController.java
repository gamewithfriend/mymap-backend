package com.sillimfive.mymap.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "user API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {


    @Operation(summary = "로드맵 학습하기", description = "Start to study the roadmap (desc)")
    @PostMapping("/roadmap-studies")
    public ResponseEntity<?> create() {
        return ResponseEntity.ok(new Object());
    }

    @Operation(summary = "학습 중인 로드맵 상세 조회", description = "Get the roadmap details on study (desc)")
    @GetMapping("/roadmap-studies/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long roadMapStudyId) {

        return ResponseEntity.ok(new Object());
    }

    @Operation(summary = "학습 중인 로드맵 수정", description = "Edit the roadmap on study (desc)")
    @PutMapping("/roadmap-studies/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") Long roadMapStudyId) {

        return ResponseEntity.ok(new Object());
    }

    @Operation(summary = "학습 중인 로드맵 목록 조회", description = "Get the roadmap list on study (desc)")
    @GetMapping("/roadmap-studies")
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(new Object());
    }
}