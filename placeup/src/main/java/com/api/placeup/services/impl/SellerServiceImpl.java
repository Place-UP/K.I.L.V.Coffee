package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.Address;
import com.api.placeup.domain.entities.Seller;
import com.api.placeup.domain.entities.User;
import com.api.placeup.domain.enums.UserType;
import com.api.placeup.domain.repositories.Addresses;
import com.api.placeup.domain.repositories.Sellers;
import com.api.placeup.exceptions.BusinessRuleException;
import com.api.placeup.rest.dto.SellerDTO;
import com.api.placeup.security.jwt.JwtService;
import com.api.placeup.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final Sellers sellersRepository;
    private final Addresses addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;

    @Override
    @Transactional
    public Seller save(SellerDTO dto) {
        if(nameAlreadyExists(dto)) {
            throw new BusinessRuleException("This name already is in use.");
        }
        if(cnpjAlreadyExists(dto)) {
            throw new BusinessRuleException("This cnpj already is in use.");
        }
        if(emailAlreadyExists(dto)) {
            throw new BusinessRuleException("This email already is in use.");
        }
        if(phoneAlreadyExists(dto)) {
            throw new BusinessRuleException("This phone already is in use.");
        }

        Address address = new Address();
        Seller seller = new Seller();
        User user = new User();


        user.setLogin(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setSeller(dto.getSeller());

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
        seller.setMute(dto.getMute());
        seller.setBlind(dto.getBlind());
        seller.setWheelchair(dto.getWheelchair());
        seller.setDeaf(dto.getDeaf());

        sellersRepository.save(seller);
        userService.save(user);

        return seller;
    }

    private boolean cnpjAlreadyExists(SellerDTO dto) {
        Optional<Seller> seller = sellersRepository.findByCnpj(dto.getCnpj());
        return seller.isPresent();
    }

    private boolean emailAlreadyExists(SellerDTO dto) {
        Optional<Seller> seller = sellersRepository.findByEmail(dto.getEmail());
        return seller.isPresent();
    }

    private boolean phoneAlreadyExists(SellerDTO dto) {
        Optional<Seller> seller = sellersRepository.findByPhone(dto.getPhone());
        return seller.isPresent();
    }

    private boolean nameAlreadyExists(SellerDTO dto) {
        Optional<Seller> seller = sellersRepository.findByName(dto.getName());
        return seller.isPresent();
    }

    @Override
    public Optional<Seller> getCompleteSeller(Integer id){
        return sellersRepository.findByIdFetchAddress(id);
    }
}
