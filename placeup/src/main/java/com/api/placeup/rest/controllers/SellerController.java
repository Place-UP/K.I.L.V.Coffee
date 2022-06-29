package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.Seller;
import com.api.placeup.rest.dto.SellerDTO;
import com.api.placeup.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService service;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Seller getSellerById(@PathVariable Integer id ){
        return service.getSellerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid SellerDTO dto) {
        return service.save(dto).getId();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        service.delete(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody @Valid SellerDTO dto){
        service.update(dto, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Seller> find( Seller filter ) {
        return service.find(filter);
    }
}