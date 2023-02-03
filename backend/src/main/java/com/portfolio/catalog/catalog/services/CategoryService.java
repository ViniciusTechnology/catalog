package com.portfolio.catalog.catalog.services;

import com.portfolio.catalog.catalog.dto.CategoryDto;
import com.portfolio.catalog.catalog.entities.Category;
import com.portfolio.catalog.catalog.repositories.CategoryRepository;
import com.portfolio.catalog.catalog.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        List<Category> list = repository.findAll();
        return list
                .stream()
                .map(e -> new CategoryDto(e))
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() ->new ResourceNotFoundException("Entity Not Found"));
        return new CategoryDto(entity);
    }
    @Transactional
    public CategoryDto insert(CategoryDto dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);

        return new CategoryDto(entity);
    }

    @Transactional
    public CategoryDto update(Long id, CategoryDto dto) {
        try {
            Category entity = repository.getReferenceById(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);

            return new CategoryDto(entity);
        }
        catch (EntityNotFoundException e){
        throw new ResourceNotFoundException("Id not found" + id);
        }
    }
}
