package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Product;
import com.api.placeup.rest.dto.ProductDTO;
import com.api.placeup.rest.dto.ProductUpdateDTO;
import com.api.placeup.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody @Valid ProductDTO dto ){
        return service.save(dto).getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid ProductUpdateDTO dto ){
        service.update(dto, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public Product getById(@PathVariable Integer id){
        return service.getById(id);
    }

    @GetMapping("/seller/{id}")
    @ResponseStatus(OK)
    public List<Product> getBySeller(@PathVariable Integer id) {
        return service.getBySeller(id);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<Product> find( Product filter, @RequestParam("order") String order) {
        return service.find(filter, order);
    }
}