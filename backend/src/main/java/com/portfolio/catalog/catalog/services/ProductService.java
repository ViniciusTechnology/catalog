package com.portfolio.catalog.catalog.services;

import com.portfolio.catalog.catalog.dto.CategoryDto;
import com.portfolio.catalog.catalog.dto.ProductDto;
import com.portfolio.catalog.catalog.entities.Category;
import com.portfolio.catalog.catalog.entities.Product;
import com.portfolio.catalog.catalog.repositories.ProductRepository;
import com.portfolio.catalog.catalog.services.exception.DataBaseException;
import com.portfolio.catalog.catalog.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDto> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(e -> new ProductDto(e));

    }
    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() ->new ResourceNotFoundException("Entity Not Found"));
        return new ProductDto(entity,entity.getCategory());
    }
    @Transactional
    public ProductDto insert(ProductDto dto) {
        Product entity = new Product();
        //entity.setName(dto.getName());
        entity = repository.save(entity);
        return new ProductDto(entity);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        try {
            Product entity = repository.getReferenceById(id);
            //entity.setName(dto.getName());
            entity = repository.save(entity);

            return new ProductDto(entity);
        }
        catch (EntityNotFoundException e){
        throw new ResourceNotFoundException("Id not found" + id);
        }
    }
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id Not Found " + id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrity violation");
        }
    }
}
