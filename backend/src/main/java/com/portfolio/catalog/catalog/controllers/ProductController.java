package com.portfolio.catalog.catalog.controllers;

import com.portfolio.catalog.catalog.dto.CategoryDto;
import com.portfolio.catalog.catalog.dto.ProductDto;
import com.portfolio.catalog.catalog.entities.Product;
import com.portfolio.catalog.catalog.services.CategoryService;
import com.portfolio.catalog.catalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy

    ) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction),
                orderBy);

        Page<ProductDto> list = service.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
       ProductDto dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> insert(@RequestBody ProductDto dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody ProductDto dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}