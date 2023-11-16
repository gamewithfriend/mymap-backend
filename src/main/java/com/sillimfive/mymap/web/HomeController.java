package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.repository.CategoryRepository;
import com.sillimfive.mymap.web.dto.CategoryRequestDto;
import com.sillimfive.mymap.web.dto.MyMapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final CategoryRepository categoryRepository;

    @PostMapping(path = "/temp/categories/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Boolean> register(@RequestBody List<CategoryRequestDto> categories) {
        List<Category> root = new ArrayList<>();
        List<Category> sub = new ArrayList<>();

        for (CategoryRequestDto dto : categories) {

            if (dto.getParentId() == null) {
                root.add(new Category(dto.getName(), null));
            }
            else {
                Category parent = categoryRepository.findById(dto.getParentId())
                        .orElseThrow(() -> new RuntimeException("NOT VALID PARENT ID"));

                sub.add(new Category(dto.getName(), parent));
            }
        }

        categoryRepository.saveAll(root);
        categoryRepository.saveAll(sub);

        return MyMapResponse.create()
                .succeed()
                .buildWith(true);
    }

}
