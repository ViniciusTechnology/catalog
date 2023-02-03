package com.portfolio.catalog.catalog.controllers;

import com.portfolio.catalog.catalog.dto.CategoryDto;
import com.portfolio.catalog.catalog.entities.Category;
import com.portfolio.catalog.catalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;
    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll(){
        List<CategoryDto> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id){
    CategoryDto dto = service.findById(id);
    return ResponseEntity.ok().body(dto);
    }
    @PostMapping
    public ResponseEntity<CategoryDto> insert(@RequestBody CategoryDto dto){
    dto = service.insert(dto);
    URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}").buildAndExpand(dto.getId())
            .toUri();
    return ResponseEntity.created(uri).body(dto);

    }
}
