package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.repository.CategoryRepository;
import com.sillimfive.mymap.web.dto.CategoryDto;
import com.sillimfive.mymap.web.dto.CategorySearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> findBy(CategorySearch categorySearch) {

        List<Category> allList = categoryRepository.findAll();
        List<CategoryDto> data;

        if (categorySearch.isRootFlag()) {
            data = allList.stream()
                    .filter(c -> c.getParent() == null)
                    .map(c -> new CategoryDto(c.getId(), c.getName(), null))
                    .collect(Collectors.toList());
        }
        else {
            data = allList.stream()
                    .filter(c -> c.getParent() != null && c.getParent().getId() == categorySearch.getParentId())
                    .map(c -> new CategoryDto(c.getId(), c.getName(), c.getParent().getId()))
                    .collect(Collectors.toList());
        }

        return data;
    }
}