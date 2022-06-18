package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.Address;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.repositories.Addresses;
import com.api.placeup.domain.repositories.Sellers;
import com.api.placeup.rest.dto.SellerDTO;
import com.api.placeup.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final Sellers sellersRepository;
    private final Addresses addressRepository;

    @Override
    @Transactional
    public Seller save(SellerDTO dto) {

        Address address = new Address();
        Seller seller = new Seller();

        address.setState(dto.getState());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setStreet(dto.getStreet());
        address.setHouseNumber(dto.getHouseNumber());
        // addressRepository.save(address);


        seller.setAddress(address);
        seller.setName(dto.getName());
        seller.setEmail(dto.getEmail());
        seller.setCnpj(dto.getCnpj());
        seller.setPhone(dto.getPhone());
        seller.setPassword(dto.getPassword());
        seller.setMute(dto.getMute());
        seller.setBlind(dto.getBlind());
        seller.setWheelchair(dto.getWheelchair());
        seller.setDeaf(dto.getDeaf());
        sellersRepository.save(seller);

        return seller;
    }

    @Override
    public Optional<Seller> getCompleteSeller(Integer id){
        return sellersRepository.findByIdFetchAddress(id);
    }
}