package com.portfolio.catalog.catalog.services;

import com.portfolio.catalog.catalog.entities.Category;
import com.portfolio.catalog.catalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Transactional
    public List<Category> findAll(){
    return repository.findAll();
    }
}
